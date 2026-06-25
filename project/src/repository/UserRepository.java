package repository;

import database.DatabaseManager;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    public void addUser(Map<String, Object> newData) throws SQLException{
        String query = "insert into users (id, name, email, phone, role) " +
                "values(?,?,?,?,?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, (String) newData.get("id"));
            ps.setString(2, (String) newData.get("name"));
            ps.setString(3, (String) newData.get("email"));
            ps.setString(4, (String) newData.get("phone"));
            ps.setString(5, (String) newData.get("role"));

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Data gagal dibuat !");
            }
        }
    }

    public Map<String, Object> getBuyerSummary(String id) throws SQLException{
        String query = "select sum(quantity) as totalQty, sum(total_price) as totalSpend from tickets " +
                "join users on tickets.user_id = users.id where users.id = ? group by users.id";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("totalTicketsPurchased", rs.getInt("totalQty"));
            result.put("totalSpending", rs.getDouble("totalSpend"));

            return result;
        }
    }

    public Map<String, Object> getOrganizerSummary(String id) throws SQLException{
        String queryTotalCreated = "select count(*) as totalCreated from events " +
                "join users on users.id = events.organizer_id where events.organizer_id = ?";

        String queryTotalRevenue = "select sum(total_price) as totalRevenue from tickets " +
                "join events on events.id = tickets.event_id where events.organizer_id = ?";

        Map<String , Object> result = new LinkedHashMap<>();

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryTotalCreated)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            result.put("totalEventsCreated", rs.getObject("totalCreated"));
        }
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryTotalRevenue)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            result.put("totalRevenue", rs.getObject("totalRevenue") == null ? 0 : rs.getObject("totalRevenue") == null);
        }

        return result;
    }

    public Map<String, Object> getUserById(String id) throws SQLException{
        String query = "select * from users where id = ?";

        String role = getRole(id);

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            Map<String ,Object> result = new LinkedHashMap<>();
            result.put("id", rs.getString("id"));
            result.put("name", rs.getString("name"));
            result.put("email", rs.getString("email"));
            result.put("phone", rs.getString("phone"));
            result.put("role", rs.getString("role"));
            result.put("createdAt", rs.getString("created_at"));

            result.put("summary", role.equals("buyer") ? getBuyerSummary(id) : getOrganizerSummary(id));

            return result;
        }
    }

    public List<User> getAllUsers(String role) throws SQLException {
        String query;
        if (role != null){
            query = "select * from users where role = ?";
        }else{
            query = "select * from users";
        }

        List<User> result = new ArrayList<>();
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            if (role != null) {
                ps.setString(1, role);
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getString("created_at")));
            }
        }
        return result;
    }

    public String getRole(String id) throws SQLException{
        String query = "select * from users where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.getString("role");
        }
    }

    /**
     *
     * @param email email yg mau dicari
     * @return true kalo email sudah dipakai, false jika blum..
     * @throws SQLException
     */
    public boolean findEmail(String email) throws SQLException{
        String query = "select * from users where email = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean findPhone(String phone) throws SQLException{
        String query = "select * from users where phone = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean findId(String id) throws SQLException{
        String query = "select * from users where id = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public Map<String, Object> updateUser(Map<String, Object> user) throws SQLException{
        String query = "update users set";
        if((String) user.get("name") != null) query += " name = ?, ";
        if((String) user.get("email") != null) query += " email = ?, ";
        if((String) user.get("phone") != null) query += " phone = ?, ";
        if((String) user.get("role") != null) query += " role = ?, ";

        String queryFinal = "";
        for(int i = 0;i < query.length()-2;i++){ // ngapus koma dan spasi
            queryFinal += query.charAt(i);
        }
        queryFinal += " where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(queryFinal)){
            int index = 1;
            if((String) user.get("name") != null) ps.setString(index++, (String) user.get("name"));
            if((String) user.get("email") != null) ps.setString(index++, (String) user.get("email"));
            if((String) user.get("phone") != null) ps.setString(index++, (String) user.get("phone"));
            if((String) user.get("role") != null) ps.setString(index++, (String) user.get("role"));
            ps.setString(index, (String) user.get("id"));

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Gagal update data user");
            }else{
                Map<String, Object> result = new LinkedHashMap<>();
                user = getUserById((String)user.get("id"));

                if((String) user.get("id") != null) result.put("id", user.get("id"));
                if((String) user.get("name") != null) result.put("name", user.get("name"));
                if((String) user.get("email") != null) result.put("email", user.get("email"));
                if((String) user.get("phone") != null) result.put("phone", user.get("phone"));
                if((String) user.get("role") != null) result.put("role", user.get("role"));

                return result;
            }
        }
    }

    public int countUser() throws  SQLException{
        String query = "select count(*) as totalUser from users";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs= ps.executeQuery();
            return rs.getInt("totalUser");
        }
    }

    public void deleteUser(User user){}
}

//CREATE TABLE IF NOT EXISTS users (\n" +
//                            "    id          TEXT PRIMARY KEY,\n" +
//                            "    name        TEXT NOT NULL,\n" +
//                            "    email       TEXT NOT NULL UNIQUE,\n" +
//                            "    phone       TEXT,\n" +
//                            "    role        TEXT DEFAULT 'buyer',       -- 'buyer' atau 'organizer'\n" +
//                            "    created_at  TEXT DEFAULT (datetime('now'))\n" +
//                            ")
