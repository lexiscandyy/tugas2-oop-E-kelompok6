package repository;
import database.DatabaseManager;
import model.Event;
import server.Response;
import server.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventRepository {

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
            ResultSet rs = ps.executeQuery();
            return rs.next();
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