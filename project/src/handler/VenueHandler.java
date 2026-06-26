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
        String id = req.getPathParam("id");
        Map<String, Object> result = VenueService.getVenueById(id);
        if (result == null) res.sendError(404, "ID tidak ditemukan");
        else res.sendSuccess(result);
    }

    public void addVenue(Request req, Response res) throws Exception{
        Map<String, Object> data = req.getJSON();

        if (data == null) {
            res.sendError(400, "Request body harus berformat JSON (Content-Type: application/json)");
            return;
        }

        String newName = (String) data.get("name");
        String newAddress = (String) data.get("address");
        Integer newMaxCapacity = (Integer) data.get("maxCapacity");

        if(
            newName == null
            || newAddress == null
            || newMaxCapacity == null
        ){
            res.sendError(400, "Wajib isi semua data");
            return;
        }

        if(newName.isEmpty() || newAddress.isEmpty()){
            res.sendError(400, "nama atau address tidak boleh kosong");
            return;
        }

        Venue result = VenueService.addVenue(data);
        res.sendSuccess(result);
    }

    public void updateVenue(Request req, Response res) throws Exception{
        Map<String, Object> data = req.getJSON();

        if (data == null) {
            res.sendError(400, "Request body harus berformat JSON (Content-Type: application/json)");
            return;
        }

        if(data.get("id") == null){
            res.sendError(400, "ID null");
            return;
        }

        String newName = (String) data.get("name");
        String newAddress = (String) data.get("address");
        Integer newMaxCapacity = (Integer) data.get("maxCapacity");

        if(newName == null && newAddress == null && newMaxCapacity == null){
            res.sendError(400, "Tidka ada data yang di update");
            return;
        }

        Map<String, Object> result = VenueService.updateVenue(data);
        if(result == null){
            res.sendError(404, "id tidak ditemukan");
            return;
        }
        res.sendSuccess(result);
    }


}
