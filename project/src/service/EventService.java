package service;

import model.Event;
import repository.EventRepository;
import repository.UserRepository;
import repository.VenueRepository;

import java.sql.SQLException;
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
            return null;
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
}
