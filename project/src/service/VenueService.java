package service;

import model.Venue;
import repository.VenueRepository;

import java.util.List;

public class VenueService {
    private VenueRepository venueRepository;

    public venueService(){
        venueRepository = new VenueRepository();
    }

    public void addVenue(Venue venue){
        venueRepository.addVenue(venue);
    }

    public Venue getVenueId(String id){
        return venueRepository.getVenueId(id);
    }

    public List<Venue> getAllVenues(){
        return venueRepository.getAllVenues();
    }

    public void updateVenue (Venue venue){
        venueRepository.updateVenue(venue);
    }

    public void deleteVenue(String id){
        venueRepository.deleteVenue(id);
    }
}
