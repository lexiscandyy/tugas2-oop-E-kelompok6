package repository;
import database.DatabaseManager;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketRepository {

    public List<Ticket> getAllTickets(String eventId, String userId, String status) throws SQLException{
        String query = "select * from tickets";
        if(eventId != null || userId != null || status != null) query += " where ";

        if(eventId != null){
            query += "event_id = ? and ";
        }

        if(userId != null){
            query += "user_id = ? and ";
        }

        if(status != null){
            query += "status = ? and ";
        }
        String queryFinal = "";
        if(query.contains("and")){
            for(int i =0;i < query.length()-4;i++){
                queryFinal += query.charAt(i);
            }
        }else queryFinal = query;

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryFinal)){
            int idx = 1;
            if(eventId != null) ps.setString(idx++, eventId);
            if(userId != null) ps.setString(idx++, userId);
            if(status != null) ps.setString(idx++, status);

            ResultSet rs = ps.executeQuery();

            List<Ticket> result = new ArrayList<>();

            while(rs.next()){
                Ticket ticket = new Ticket(
                    rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getDouble("total_price"),
                        rs.getString("purchase_date"),
                        rs.getString("status"),
                        rs.getDouble("refund_amount")
                );

                result.add(ticket);
            }
            return result;
        }
    }

    public Map<String, Object> getTicketById(String id) throws SQLException{
        String query = "select * from tickets where id= ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Map<String,Object> result = new LinkedHashMap<>();

            result.put("id",rs.getString("id"));
            result.put("eventId", rs.getString("event_id"));
            result.put("userId", rs.getString("user_id"));
            result.put("category", rs.getString("category"));
            result.put("quantity", rs.getInt("quantity"));
            result.put("unitPrice", rs.getDouble("unit_price"));
            result.put("totalPrice", rs.getDouble("total_price"));
            result.put("purchaseDate", rs.getString("purchaseDate"));
            result.put("status", rs.getString("status"));
            result.put("refundAmount", rs.getDouble("refund_amount"));

            return result;
        }
    }

    public boolean findId(String id) throws  SQLException{
        String query = "select * from tickets where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public int countTickets() throws  SQLException{
        String query = "select count(*) as total from tickets";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            return rs.getInt("total");
        }
    }

    public Map<String, Object> addTicket(Map<String, Object> data) throws SQLException{
        String query = "insert into tickets (id, event_id, user_id, category, quantity, unit_price, total_price " +
                "values(?,?,?,?,?,?,?)";

        String id= (String) data.get("id");
        String eventId = (String) data.get("eventId");
        String userId = (String) data.get("userId");
        String category = (String) data.get("category");
        Integer quantity = (Integer) data.get("quantity");

        double unitPrice;
        double totalPrice;

        Event event;
        if(category.equals("concert") ){
            event = new Concert();
        }else if(category.equals("seminar")){
            event = new Seminar();
        }else event = new SportMatch();

        unitPrice = event.calculateTicketPrice(category);
        totalPrice = unitPrice * quantity;

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ps.setString(2, eventId);
            ps.setString(3, userId);
            ps.setString(4, category);
            ps.setInt(5, quantity);
            ps.setDouble(6, unitPrice);
            ps.setDouble(7, totalPrice);

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0) {
                throw new SQLException("tidak dapat membuat data");
            }
            Map<String, Object> result = getTicketById(id);
            result.put("unitPrice", unitPrice);
            result.put("totalPrice", totalPrice);

            return result;
        }
    }
}
