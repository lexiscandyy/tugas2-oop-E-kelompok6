package service;

import model.Venue;
import repository.VenueRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VenueService {
    private static VenueRepository venueRepository;

    public static String generateId() throws SQLException {
        String result = "VNU-";
        result += String.valueOf(venueRepository.countVenues() + 1);
        return result;
    }

    public static List<Venue> getAllVenues() throws SQLException {
        return venueRepository.getAllVenues();
    }

    public static Venue getVenueById(String id) throws SQLException {
        return venueRepository.getVenueById(id);
    }

    public static Venue addVenue(Map<String, Object> newVenue) throws SQLException {
        String newId = generateId();
        newVenue.put("id", newId);
        return venueRepository.addVenue(newVenue);
    }


    public static Venue updateVenue (Map<String, Object> newData) throws SQLException{
        if(venueRepository.findId((String) newData.get("id")) == false){
            return null;
        }

        return venueRepository.updateVenue(newData);
    }

//    public void deleteVenue(String id){
//        venueRepository.deleteVenue(id);
//    }
}
