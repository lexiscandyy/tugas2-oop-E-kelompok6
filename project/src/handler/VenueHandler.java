package handler;

import server.Request;
import server.Response;
import model.Venue;
import service.VenueService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VenueHandler {
    public void getAllVenues(Request req, Response res) throws Exception {
        List<Venue> result = VenueService.getAllVenues();
        res.sendSuccess(result);
    }

    public void getVenueById(Request req, Response res) throws Exception{
        Venue result = VenueService.getVenueById(req.getPathParam("id"));
        if (result == null) res.sendError(404, "ID tidak ditemukan");
        else res.sendSuccess(result);
    }

    public void addVenue(Request req, Response res) throws Exception{
        Map<String, Object> data = req.getJSON();

    }

    public void updateVenue(Request req, Response res) throws Exception{

    }


}
