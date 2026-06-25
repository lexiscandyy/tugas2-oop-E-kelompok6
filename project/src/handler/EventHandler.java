package handler;

import server.Request;
import server.Response;
import service.EventService;

import java.util.Map;

public class EventHandler {
    public void getAllEvents(Request req, Response res){

    }

    public void getPriceSummary(Request req, Response res) {
    }

    public void getEventById(Request req, Response res) {
    }

    public void addEvent(Request req, Response res) throws Exception {
        Map<String, Object> data = req.getJSON();

        String type = (String)data.get("type");
        String name = (String) data.get("name");
        String venueId = (String) data.get("venueId");
        String organizerId = (String) data.get("organizerId");
        String date = (String) data.get("date");
        Integer basePrice = (Integer) data.get("basePrice");
        Map<String, Integer> capacity = (Map<String, Integer>) data.get("capacity");

        if(type == null
                || name == null
                || venueId == null
                || organizerId == null
                || date == null
                || basePrice == null
                || capacity == null){
            res.sendError(400, "data harus terisi semua");
            return;
        }

        if(type.equals("seminar") == false && type.equals("sport_match") == false && type.equals("concert") == false){
            res.sendError(400, "type harus berupa seminar/sport_match/concert");
            return;
        }

        if(capacity.containsKey("vip") == false
        && capacity.containsKey("regular") == false
        && capacity.containsKey("festival") == false
        && capacity.containsKey("tribune") == false
        && capacity.containsKey("vvip") == false
        ){
            res.sendError(400, "category capacity harus vip/regular/festival/tribune/vvip");
            return;
        }

        Map<String, Object> result = EventService.addEvent(data);
        if(result == null){
            res.sendError(404, "gagal membuat event: venueId salah/organizerId salah");
            return;
        }
        res.sendSuccess(result);
    }

    public void updateEvent(Request req, Response res) {
    }
}
