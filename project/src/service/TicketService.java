package service;

import exception.EventNotFoundException;
import exception.TicketSoldOutException;
import model.Ticket;
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

        Map<String, Object> capacity = eventRepository.getEventCapacity(eventId);
        if((Integer) capacity.get(category) < quantity){
            throw new TicketSoldOutException(String.format("Kapasitas %s untuk eventId %s sudah abis",category, eventId));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> eventData = eventRepository.getEventById(eventId);
        Map<String, Object> userData = userRepository.getUserById(userId);

        data.put("id", id);

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

}
