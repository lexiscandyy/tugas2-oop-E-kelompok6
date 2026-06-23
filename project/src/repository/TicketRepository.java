package repository;
import database.DatabaseManager;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRepository {

    public void insert(Ticket ticket) throws SQLException {
        String query = "insert into ticket (id, event_id, user_id, category, quantity, unit_price, total_price)" +
                " values (?, ?, ?, ?, ?, ?, ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ticket.getId());
            ps.setString(2, ticket.getEventId());
            ps.setString(3, ticket.getUserId());
            ps.setString(4, ticket.getCategory());
            ps.setInt(5,    ticket.getQuantity());
            ps.setDouble(6, ticket.getUnitPrice());
            ps.setDouble(7, ticket.getTotalPrice());
            ps.executeUpdate();
        }
    }

}
