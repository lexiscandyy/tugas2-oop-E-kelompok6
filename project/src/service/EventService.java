package service;

import model.*;
import repository.EventRepository;
import repository.UserRepository;
import repository.VenueRepository;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EventService {
    private static EventRepository eventRepository = new EventRepository();
    private static UserRepository userRepository = new UserRepository();
    private static VenueRepository venueRepository = new VenueRepository();

    public static String generateId() throws SQLException {
        String result = "EVT-";
        result += String.valueOf(eventRepository.countEvents() + 1);
        return result;
    }

    public static Map<String, Object> addEvent(Map<String, Object> data) throws SQLException {
        data.put("id", generateId());

        String venueId = (String)data.get("venueId");
        String organizerId=(String) data.get("organizerId");

        if(venueRepository.findId(venueId) == false){
            return null;
        }

        if(userRepository.findId(organizerId) == false){
            return null;
        }

        Map<String, Object> user = userRepository.getUserById(organizerId);
        if(((String) user.get("role")).equals("organizer") == false){
            throw new IllegalArgumentException("USER bukan merupakan organizer");
        }

        int capacityCount = 0;
        Map<String, Integer> capacity = (Map<String, Integer>) data.get("capacity");

        if(capacity.containsKey("regular")) capacityCount += capacity.get("regular");
        if(capacity.containsKey("vip")) capacityCount += capacity.get("vip");
        if(capacity.containsKey("vvip")) capacityCount += capacity.get("vvip");
        if(capacity.containsKey("festival")) capacityCount += capacity.get("festival");
        if(capacity.containsKey("tribune")) capacityCount += capacity.get("tribune");

        if(capacityCount > venueRepository.getCapacity(venueId)){
            throw new IllegalArgumentException("Kapasitas event melebihi kapasitas maksimal venue");
        }

        String date = (String) data.get("date");
        if(eventRepository.findDate(date)){
            throw new IllegalArgumentException("terdapat event yang memiliki date yang sama");
        }

        return eventRepository.addEvent(data);
    }

    public static List<Map<String, Object>> getAllEvents(String type, String dateFrom)throws SQLException {
        return eventRepository.getAllEvents(type, dateFrom);
    }


    public static Map<String, Object> getEventById(String id) throws SQLException{

        if(eventRepository.findId(id) == false){
            return null;
        }

        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, Object> eventData = eventRepository.getEventById(id);

        String type =(String) eventData.get("type");
        String name = (String) eventData.get("name");
        String venueId = (String) eventData.get("venueId");
        String organizerId = (String) eventData.get("organizerId");
        String date = (String) eventData.get("date");
        Integer basePrice = (Integer) eventData.get("basePrice");
        String createdAt = (String) eventData.get("createdAt");

        Map<String, Object> userData = userRepository.getUserById(organizerId);
        Map<String, Object> venueData = venueRepository.getVenueById(venueId);

        Map<String, Object> userResult = new LinkedHashMap<>(), venueResult = new LinkedHashMap<>();
        userResult.put("id",(String) userData.get("id"));
        userResult.put("name", (String) userData.get("name"));
        venueResult.put("id", (String) venueData.get("id"));
        venueResult.put("name", (String) venueData.get("name"));

        Map<String, Object> priceListResult = new LinkedHashMap<>();
        Map<String, Object> capacity = eventRepository.getEventCapacity(id);
        Map<String, Object> remainingCapacity = new LinkedHashMap<>();

        if(capacity.containsKey("vip")) remainingCapacity.put("vip", (Integer) capacity.get("vip") - (Integer) capacity.get("filled"));
        if(capacity.containsKey("regular")) remainingCapacity.put("regular", (Integer) capacity.get("regular") - (Integer) capacity.get("filled"));
        if(capacity.containsKey("festival")) remainingCapacity.put("festival", (Integer) capacity.get("festival") - (Integer) capacity.get("filled"));
        if(capacity.containsKey("tribune")) remainingCapacity.put("tribune", (Integer) capacity.get("tribune") - (Integer) capacity.get("filled"));
        if(capacity.containsKey("vvip")) remainingCapacity.put("vvip", (Integer) capacity.get("vvip") - (Integer) capacity.get("filled"));

        Event eventType;

        if(type.equals("concert")){
            eventType = new Concert();
        }else if(type.equals("seminar")){
            eventType = new Seminar();
        }else{
            eventType = new SportMatch();
        }

        eventType.setType(type);
        eventType.setId(id);
        eventType.setName(name);
        eventType.setVenueId(venueId);
        eventType.setOrganizerId(organizerId);
        eventType.setDate(date);
        eventType.setBasePrice(basePrice);

        if(capacity.containsKey("vip")) priceListResult.put("vip", eventType.calculateTicketPrice("vip"));
        if(capacity.containsKey("regular")) priceListResult.put("regular", eventType.calculateTicketPrice("regular"));
        if(capacity.containsKey("festival")) priceListResult.put("festival", eventType.calculateTicketPrice("festival"));
        if(capacity.containsKey("tribune")) priceListResult.put("tribune", eventType.calculateTicketPrice("tribune"));
        if(capacity.containsKey("vvip")) priceListResult.put("vvip", eventType.calculateTicketPrice("vvip"));

        result.put("id", id);
        result.put("type", type);
        result.put("name", name);
        result.put("venue", venueResult);
        result.put("organizer", userResult);
        result.put("date", date);
        result.put("basePrice", basePrice);
        result.put("priceList", priceListResult);
        result.put("remainingCapacity", remainingCapacity);

        if(eventType instanceof Refundable){
            result.put("refundable", ((Refundable) eventType).isRefundable());
            result.put("refundPolicy", "100% if >14 days, 50% if 7-14 days, 0% if <7 days");
        }

        return result;
    }

    public static Map<String, Object> updateEvent(Map<String, Object> data) throws SQLException{
        return eventRepository.updateEvent(data);
    }
}
