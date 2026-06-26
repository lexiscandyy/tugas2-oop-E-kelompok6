package handler;

import model.Event;
import server.Request;
import server.Response;
import service.EventService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Map;

public class EventHandler {
    public void getAllEvents(Request req, Response res) throws Exception {
        String type = req.getQueryParam("type");
        String dateFrom = req.getQueryParam("dateFrom");
        List<Map<String, Object>> result = EventService.getAllEvents(type, dateFrom);
        res.sendSuccess(result);
    }

    public void getPriceSummary(Request req, Response res) throws Exception {
        List<Map<String, Object>> result = EventService.getPriceSummary();
        res.sendSuccess(result);
    }

    public void getEventById(Request req, Response res) throws Exception {
        String id = req.getPathParam("id");
        Map<String, Object> result = EventService.getEventById(id);
        if(result == null){
            res.sendError(404, "event id tidak ditemukan");
            return;
        }
        res.sendSuccess(result);
    }

    public void addEvent(Request req, Response res) throws Exception {
        Map<String, Object> data = req.getJSON();
        if (data == null) {
            res.sendError(400, "Request body harus berformat JSON");
            return;
        }
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

        if(isValidDate(date) == false){
            res.sendError(400, "format date harus 'YYYY-MM-DD'");
            return;
        }

        Map<String, Object> result = EventService.addEvent(data);
        if(result == null){
            res.sendError(404, "gagal membuat event: venueId salah/organizerId salah");
            return;
        }
        res.sendSuccess(result);
    }

    public void updateEvent(Request req, Response res) throws Exception {
        Map<String, Object> data = req.getJSON();
        if (data == null) {
            res.sendError(400, "Request body harus berformat JSON");
            return;
        }
        String id = req.getPathParam("id");

        data.put("id" , id);
        String newName = (String) data.get("name");
        String newDate=  (String) data.get("date");
        Integer newBasePrice = (Integer) data.get("basePrice");

        if(newName == null && newDate == null && newBasePrice == null){
            res.sendError(400, "Tidak ada data yg bisa diudptae");
            return;
        }

        Map<String, Object> result = EventService.updateEvent(data);

        if(result == null){
            res.sendError(404, "id event tidak ditemukan");
            return;
        }

        res.sendSuccess(result);
    }

    public static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
