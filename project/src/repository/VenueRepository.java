package repository;

import database.DatabaseManager;
import model.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenueRepository {
    public void addVenue(Venue venue){}
    public User getVenueId(String id){}

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

    public Venue getVenueById(String id) throws SQLException{
        String query = "select * from venues where id = " + id;

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false) return null;

            Venue result = new Venue(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getInt("maxCapacity"),
                    rs.getString("createdAt")
            );
            return result;
        }
    }

    public void updateVenue(Venue venue){}
    public void deleteVenue(Venue venue){}
}
//"CREATE TABLE IF NOT EXISTS venues (\n" +
//                            "    id              TEXT PRIMARY KEY,\n" +
//                            "    name            TEXT NOT NULL,\n" +
//                            "    address         TEXT NOT NULL,\n" +
//                            "    max_capacity    INTEGER NOT NULL,\n" +
//                            "    created_at      TEXT DEFAULT (datetime('now'))\n" +
//                            ")