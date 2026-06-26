package repository;
import database.DatabaseManager;
import model.*;
import server.Response;
import server.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventRepository {

    public List<Map<String, Object>> getAllEvents(String type, String dateFrom) throws SQLException{
        String query = "select * from events";
        if(type != null && dateFrom != null) {
            query += " where type = ? and date >= ?";
        }else if(type != null){
            query += " where type = ?";
        }else if(dateFrom != null){
            query += " where date >= ?";
        }

        List<Map<String, Object>> result = new ArrayList<>();

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            int idx = 1;
            if(type != null) ps.setString(idx++, type);
            if(dateFrom != null) ps.setString(idx++, dateFrom);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Map<String, Object> tmp = new LinkedHashMap<>();
                tmp.put("id", rs.getString("id"));
                tmp.put("type", rs.getString("type"));
                tmp.put("name", rs.getString("name"));
                tmp.put("venueId", rs.getString("venue_id"));
                tmp.put("organizerId", rs.getString("organizer_id"));
                tmp.put("date", rs.getString("date"));
                tmp.put("basePrice", rs.getString("base_price"));
                tmp.put("createdAt", rs.getString("created_at"));
                tmp.put("capacity", getEventCapacity(rs.getString("id")));
                result.add(tmp);
            }
            return result;
        }
    }

    public Map<String, Object> getEventById(String id) throws SQLException{
        String query = "select * from events where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            String type = rs.getString("type");
            String name = rs.getString("name");
            String venueId = rs.getString("venue_id");
            String organizerId = rs.getString("organizer_id");
            String date = rs.getString("date");
            Double basePrice = rs.getDouble("base_price");
            String createdAt = rs.getString("created_at");

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("id", id);
            result.put("type", type);
            result.put("name", name);
            result.put("venueId", venueId);
            result.put("organizerId", organizerId);
            result.put("date", date);
            result.put("basePrice", basePrice);
            result.put("createdAt", createdAt);

            return result;
        }
    }

    public Map<String, Object> addEvent(Map<String, Object> data) throws SQLException{
        String query = "insert into events (id, type, name, venue_id, organizer_id, date, base_price) " +
                "values(?,?,?,?,?,?,?)";

//        Map<String, Object> result;

        String id = (String) data.get("id");
        String type = (String) data.get("type");
        String name = (String) data.get("name");
        String venueId = (String) data.get("venueId");
        String organizerId= (String) data.get("organizerId");
        String date = (String) data.get("date");
        Integer basePrice = (Integer) data.get("basePrice");
        Map<String, Integer> capacity = (Map<String, Integer>) data.get("capacity");

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, id);
            ps.setString(2, type);
            ps.setString(3, name);
            ps.setString(4, venueId);
            ps.setString(5, organizerId);
            ps.setString(6, date);
            ps.setInt(7, basePrice);

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("gagal membuat event baru");
            }
//            result = data;
        }

        if(capacity.containsKey("regular")) addCapacity(id, "regular", (capacity.get("regular")));
        if(capacity.containsKey("vip")) addCapacity(id, "vip", (capacity.get("vip")));
        if(capacity.containsKey("vvip")) addCapacity(id, "vvip", (capacity.get("vvip")));
        if(capacity.containsKey("festival")) addCapacity(id, "festival", (capacity.get("festival")));
        if(capacity.containsKey("tribune")) addCapacity(id, "tribune", (capacity.get("tribune")));

        return data;
    }

    public void addCapacity(String eventId, String category, int total) throws SQLException{
        String query = "insert into capacities (event_id, category, total) " +
                "values(?,?,?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, eventId);
            ps.setString(2, category);
            ps.setInt(3, total);
            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("gagal membuat capacity");
            }
        }
    }



    public Map<String, Object> getEventCapacity(String eventId) throws SQLException{
        String query = "select * from capacities where event_id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            Map<String, Object> result = new LinkedHashMap<>();
            while(rs.next()){
                result.put(rs.getString("category"), rs.getInt("total"));
                result.put("filled_" + rs.getString("category"), rs.getInt("filled"));
            }
            return result;
        }
    }

    public Map<String, Object> updateEvent(Map<String, Object> data) throws SQLException{
        String query = "update events set ";
        if(data.containsKey("name")) query += "name = ?, ";
        if(data.containsKey("date")) query += "date = ?, ";
        if(data.containsKey("basePrice")) query += "base_price = ?, ";

        String queryFinal = "";
        for(int i = 0;i < query.length()-2;i++){
            queryFinal += query.charAt(i);
        }

        queryFinal += "where id = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryFinal)){
            int idx =1;

            if(data.containsKey("name")) ps.setString(idx++, (String) data.get("name"));
            if(data.containsKey("date")) ps.setString(idx++, (String) data.get("date"));
            if(data.containsKey("basePrice")) ps.setString(idx++, (String) data.get("basePrice"));

            ResultSet rs = ps.executeQuery();
            Map<String, Object> result = getEventById((String) data.get("id"));
            return result;

        }
    }

    public int countEvents()throws  SQLException {
        String query = "select count(*) as total from events";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            return rs.getInt("total");
        }
    }

    public boolean findDate(String date) throws SQLException{
        String query = "select * from events where date = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean findId(String id) throws SQLException{
        String query = "select * from events where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean findCapacityCategory(String eventId, String category) throws SQLException{
        String query = "select * from capacities where event_id = ? and category = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, eventId);
            ps.setString(2, category);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public List<Map<String, Object>> getPriceSummary() throws SQLException{
        String query = "select * from events";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            List<Map<String, Object>> result = new ArrayList<>();
            while(rs.next()){
                Map<String, Object> tmp = new LinkedHashMap<>();
                Event event;
                String type = rs.getString("type");
                if(type.equals("concert")){
                    event = new Concert();
                }else if(type.equals("seminar")) event = new Seminar();
                else event = new SportMatch();

                event.setName(rs.getString("name"));
                event.setType(type);
                event.setBasePrice(rs.getDouble("base_price"));

                Map<String, Object> capacity = getEventCapacity(rs.getString("id"));
                Map<String, Double> prices = new LinkedHashMap<>();

                if(capacity.containsKey("regular")) prices.put("regular", event.calculateTicketPrice("regular"));
                if(capacity.containsKey("vip")) prices.put("vip", event.calculateTicketPrice("vip"));
                if(capacity.containsKey("vvip")) prices.put("vvip", event.calculateTicketPrice("vvip"));
                if(capacity.containsKey("tribune")) prices.put("tribune", event.calculateTicketPrice("tribune"));
                if(capacity.containsKey("festival")) prices.put("festival", event.calculateTicketPrice("festival"));

                tmp.put("id", rs.getString("id"));
                tmp.put("name", event.getName());
                tmp.put("type", event.getType());
                tmp.put("prices", prices);
                result.add(tmp);
            }
            return result;
        }
    }
}
// id              TEXT PRIMARY KEY,\n" +
//                            "    type            TEXT NOT NULL,           -- 'concert', 'seminar', 'sport_match'\n" +
//                            "    name            TEXT NOT NULL,\n" +
//                            "    venue_id        TEXT NOT NULL,\n" +
//                            "    organizer_id    TEXT NOT NULL,           -- FK ke users (role = 'organizer')\n" +
//                            "    date            TEXT NOT NULL,           -- format: YYYY-MM-DD\n" +
//                            "    base_price      REAL NOT NULL,\n" +
//                            "    created_at      TEXT DEFAULT (datetime('now')),\n" +