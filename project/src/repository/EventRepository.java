package repository;
import database.DatabaseManager;
import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepository {

    public void insert(Event event) throws SQLException {
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
            ps.setString(7, toString(event.getBasePrice()));
            ps.setString(8, event.getCreatedAt());
        }
    }

}
