package repository;

import database.DatabaseManager;
import model.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VenueRepository {

    public Venue addVenue(Map<String, Object> newVenue) throws SQLException{
        String query = "insert into venues (id, name, address, max_capacity) " +
                "values (?, ?, ?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, (String) newVenue.get("id"));
            ps.setString(2, (String) newVenue.get("name"));
            ps.setString(3, (String) newVenue.get("address"));
            ps.setInt(4, (int) newVenue.get("maxCapacity"));

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("gagal membuat data");
            }else{
                Venue result = new Venue(
                        (String) newVenue.get("id"),
                        (String) newVenue.get("name"),
                        (String) newVenue.get("address"),
                        (int) newVenue.get("maxCapacity"),
                        "Now"
                );
                return result;
            }
        }
    }

    public List<Venue> getAllVenues() throws SQLException {
        String query = "select * from venues";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            List<Venue> result = new ArrayList<>();
            while(rs.next()){
                result.add(new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("max_capacity"),
                        rs.getString("created_at")
                ));
            }
            return result;
        }
    }

    public List<Map<String, Object>> getEventsVenue(String id) throws SQLException{
        String query = "select * from events join venues on venues.id = events.venue_id where venues.id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);

            List<Map<String, Object>> result = new ArrayList<>();

            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Map<String, Object> tmp = new LinkedHashMap<>();
                tmp.put("id", rs.getString("id"));
                tmp.put("name", rs.getString("name"));
                tmp.put("date", rs.getString("date"));

                result.add(tmp);
            }
            return result;
        }
    }

    public Map<String, Object> getVenueById(String id) throws SQLException{
        String query = "select * from venues where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false) return null;

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("id", rs.getString("id"));
            result.put("name", rs.getString("name"));
            result.put("address", rs.getString("address"));
            result.put("maxCapacity", rs.getInt("max_capacity"));
            result.put("createdAt", rs.getString("created_at"));

            result.put("events", getEventsVenue(id));
            return result;
        }
    }

    public int getCapacity(String venueId) throws SQLException{
        String query = "select max_capacity from venues where id = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, venueId);
            ResultSet rs = ps.executeQuery();
            return rs.getInt("max_capacity");
        }
    }

    public Map<String, Object> updateVenue(Map<String, Object> newData) throws SQLException{
        String query = "update venues set  ";
        if(newData.get("name") != null) query += "name = ?, ";
        if(newData.get("address") != null) query += "address = ?, ";
        if(newData.get("maxCapacity") != null) query += "max_capacity = ?, ";

        String queryFinal = "";
        for(int i =0;i<query.length()-2;i++){
            queryFinal += query.charAt(i);
        }
        queryFinal += " where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryFinal)){
            int idx= 1;

            if(newData.get("name") != null) ps.setString(idx++, (String) newData.get("name"));
            if(newData.get("address") != null) ps.setString(idx++, (String) newData.get("address"));
            if(newData.get("maxCapacity") != null) ps.setInt(idx++, (Integer) newData.get("maxCapacity"));

            ps.setString(idx, (String) newData.get("id"));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("gagal update venue");
            }
            Map<String, Object> result = getVenueById((String) newData.get("id"));
            result.remove("events");
            return result;
        }
    }
    public void deleteVenue(Venue venue){}

    public int countVenues() throws SQLException{
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement("select count(*) as totalData from venues")){
            ResultSet rs = ps.executeQuery();
            return rs.getInt("totalData");
        }
    }

    public boolean findId(String id) throws SQLException{
        String query = "select * from venues where id = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
//"CREATE TABLE IF NOT EXISTS venues (\n" +
//                            "    id              TEXT PRIMARY KEY,\n" +
//                            "    name            TEXT NOT NULL,\n" +
//                            "    address         TEXT NOT NULL,\n" +
//                            "    max_capacity    INTEGER NOT NULL,\n" +
//                            "    created_at      TEXT DEFAULT (datetime('now'))\n" +
//                            ")