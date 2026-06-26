package handler;

import model.Ticket;
import server.Request;
import server.Response;
import service.TicketService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TicketHandler {
    public void getAllTickets(Request req, Response res) throws Exception{
        String eventId = req.getQueryParam("eventId");
        String userId = req.getQueryParam("userId");
        String status = req.getQueryParam("status");

        List<Ticket> result = TicketService.getAllTickets(eventId, userId, status);
        res.sendSuccess(result);
    }

    public void getTicketById(Request req, Response res) throws Exception {
        String id= req.getPathParam("id");

        Map<String ,Object> result = TicketService.getTicketById(id);
        if(result == null){
            res.sendError(404, "id ticket tidak ditemukan");
            return;
        }
        res.sendSuccess(result);
    }

    public void addTicket(Request req, Response res) throws Exception {
        Map<String, Object> data = req.getJSON();

        if (data == null) {
            res.sendError(400, "Request body harus berformat JSON");
            return;
        }

        String eventId = (String) data.get("eventId");
        String userId = (String) data.get("userId");
        String category = (String) data.get("category");
        Integer quantity = (Integer) data.get("quantity");

        if(eventId == null || userId == null || category == null || quantity == null){
            res.sendError(400, "data harus lengkap, eventId, userId, category, quantity");
            return;
        }

        Map<String, Object> result = TicketService.addTicket(data);
        if(result == null){
            res.sendError(404, "userId/eventId tidak ditemukan/quantity lebih");
            return;
        }

        res.sendSuccess(result);
    }

    public void refundTicket(Request req, Response res) throws Exception {
        String id = req.getPathParam("id");
        Map<String, Object> result = TicketService.refundTicket(id);
        if (result == null){
            res.sendError(404, "id tiket tidak ditemukan");
            return;
        }

        res.sendSuccess(result);
    }

    public void reports(Request req, Response res) throws Exception {
        String id= req.getQueryParam("eventId");
        Map<String, Object> result = TicketService.getEventReports(id);

        if(res==null){
            res.sendError(404, "id event tidak ditemukan");
            return;
        }

        res.sendSuccess(result);
    }
}
