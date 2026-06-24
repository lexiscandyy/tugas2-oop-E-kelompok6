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

    public boolean insert(Event event) throws SQLException {
        String query = "insert into events (id, type, name, venue_id, organizer_id, date, base_price, created_at) " +
                "values (?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, event.getId());
            ps.setString(2, event.getType());
            ps.setString(3, event.getName());
            ps.setString(4, event.getVenueId());
            ps.setString(5, event.getOrganizerId());
            ps.setString(6, event.getDate());
            ps.setString(7, Double.toString(event.getBasePrice()));
            ps.setString(8, event.getCreatedAt());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Map<String, Object>> selectAll() throws SQLException {
        String query = "select * from events";

        List<Map<String, Object>> result = new ArrayList<>();
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Map<String, Object> newData = new LinkedHashMap<>();
                newData.put("id", rs.getString("id"));
                newData.put("type", rs.getString("type"));
                newData.put("name", rs.getString("name"));
                newData.put("venueId", rs.getString("venue_id"));
                newData.put("organizedId", rs.getString("organizer_id"));
                newData.put("date", rs.getString("date"));
                newData.put("basePrice", rs.getDouble("base_price"));
                newData.put("createdAt", rs.getString("created_at"));

                result.add(newData);
            }
        }

        return result;
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