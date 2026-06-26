package service;

import exception.EventNotFoundException;
import exception.RefundNotAllowedException;
import exception.TicketSoldOutException;
import model.*;
import repository.EventRepository;
import repository.TicketRepository;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketService {
    private static TicketRepository ticketRepository = new TicketRepository();
    private static UserRepository userRepository = new UserRepository();
    private static EventRepository eventRepository = new EventRepository();

    public static String generateId() throws SQLException {
        String result = "TKT-";
        result += String.valueOf(ticketRepository.countTickets() + 1);
        return result;
    }

    public static List<Ticket> getAllTickets(String eventId, String userId, String status) throws SQLException {
        return ticketRepository.getAllTickets(eventId, userId, status);
    }

    public static Map<String, Object> getTicketById(String id) throws SQLException{
        if(ticketRepository.findId(id) == false){
            return null;
        }

        Map<String, Object> result = ticketRepository.getTicketById(id);

        Map<String, Object> userData = userRepository.getUserById((String) result.get("userId"));
        Map<String, Object> eventData = eventRepository.getEventById((String) result.get("eventId"));
        Map<String, Object> eventResult = new LinkedHashMap<>();

        eventResult.put("id",(String) eventData.get("id"));
        eventResult.put("name", (String) eventData.get("name"));
        eventResult.put("date", (String) eventData.get("date"));

        result.put("userId", userData);
        result.put("eventId", eventResult);

        return result;
    }

    public static Map<String, Object> addTicket(Map<String, Object> data) throws SQLException{
        String id = generateId();
        String eventId = (String) data.get("eventId");
        String userId = (String) data.get("userId");
        String category = (String) data.get("category");
        Integer quantity = (Integer) data.get("quantity");

        if(eventRepository.findId(eventId) == false){
            throw new EventNotFoundException("event tidak ditemukan");
        }

        if(userRepository.findId(userId) == false){
            return null;
        }

        if(eventRepository.findCapacityCategory(eventId, category) == false){
            throw new IllegalArgumentException(String.format("tidak terdapat kategori %s dalam eventId %s", category, eventId));
        }

        Map<String, Object> capacity = eventRepository.getEventCapacity(eventId);
        if((Integer) capacity.get(category) - (Integer) capacity.get("filled_" + category) < quantity){
            throw new TicketSoldOutException(String.format("Jumlah Kapasitas %s untuk eventId %s tidak cukup" ,category, eventId));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> eventData = eventRepository.getEventById(eventId);
        Map<String, Object> userData = userRepository.getUserById(userId);

        data.put("id", id);
        data.put("type", eventData.get("type"));
        data = ticketRepository.addTicket(data);

        Map<String,Object> buyer = new LinkedHashMap<>();
        buyer.put("id", userData.get("id"));
        buyer.put("name", userData.get("name"));

        result.put("id", id);
        result.put("event",eventData.get("name"));
        result.put("eventType",  eventData.get("type"));
        result.put("buyer", buyer);
        result.put("category", category);
        result.put("quantity", quantity);
        result.put("unitPrice", data.get("unitPrice"));
        result.put("totalPrice", data.get("totalPrice"));
        result.put("purchaseDate", data.get("purchaseDate"));
        result.put("status", "active");

        return result;
    }

    public static Map<String, Object> refundTicket(String id) throws SQLException {

        if (ticketRepository.findId(id) == false) {
            return null;
        }

        if(ticketRepository.isRefunded(id) == true){
            throw new RefundNotAllowedException(String.format("Ticket dengan id %s sudah di refund", id));
        }

        Map<String, Object> ticketData = ticketRepository.getTicketById(id);
        Map<String, Object> eventData = eventRepository.getEventById((String) ticketData.get("eventId"));
        String type = (String) eventData.get("type");

        Event event;
        if (type.equals("concert")) {
            event = new Concert();
        } else if (type.equals("seminar")) {
            event = new Seminar();
        } else event = new SportMatch();

        event.setId((String) eventData.get("id"));
        event.setType((String) eventData.get("type"));
        event.setName((String) eventData.get("name"));
        event.setVenueId((String) eventData.get("venueId"));
        event.setOrganizerId((String) eventData.get("organizerId"));
        event.setDate((String) eventData.get("date"));
        event.setBasePrice((Double) eventData.get("basePrice"));

        if ((event instanceof Refundable) == false) {
            throw new RefundNotAllowedException("SportMatch events do not support refunds");
        }

        String ticketPurchaseDate = (String) ticketData.get("purchaseDate");
        String eventDate = (String) eventData.get("date");
        int daysBeforeEvent = ticketRepository.calculateRemainingDaysBeforeEvent(ticketPurchaseDate, eventDate);

        double refundPercentage = ((Refundable) event).calculateRefund(daysBeforeEvent);
        double refundAmount =  refundPercentage * event.calculateTicketPrice((String) ticketData.get("category")) * (Integer) ticketData.get("quantity");

        ticketRepository.updateTicket(id, refundAmount);
        ticketRepository.updateCapacity((Integer) ticketData.get("quantity") * -1, (String) ticketData.get("eventId"), (String) ticketData.get("category"));

        ticketData = ticketRepository.getTicketById(id);
        eventData = eventRepository.getEventById((String) ticketData.get("eventId"));

        Map<String ,Object> result = new LinkedHashMap<>();

        result.put("id", id);
        result.put("event", eventData.get("name"));
        result.put("totalPaid", ticketData.get("totalPrice"));
        result.put("refundedPercentage", refundPercentage * 100);
        result.put("refundAmount", refundAmount);
        result.put("status", "refunded");

        return result;
    }
}
