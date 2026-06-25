package service;

import model.Venue;
import repository.VenueRepository;

import java.sql.SQLException;
import java.util.List;

public class VenueService {
    private static VenueRepository venueRepository;

    public static List<Venue> getAllVenues() throws SQLException {
        return venueRepository.getAllVenues();
    }

    public static Venue getVenueById(String id) throws SQLException {
        return venueRepository.getVenueById(id);
    }

    public void addVenue(Venue venue){
        venueRepository.addVenue(venue);
    }

    public Venue getVenueId(String id){
        return venueRepository.getVenueId(id);
    }

    public void updateVenue (Venue venue){
        venueRepository.updateVenue(venue);
    }

    public void deleteVenue(String id){
        venueRepository.deleteVenue(id);
    }
}
