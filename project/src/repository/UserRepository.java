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

    public User getUserById(String id) throws SQLException{
        String query = "select * from users where id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            User result = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("created_at")
            );
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

    public User updateUser(Map<String, Object> user) throws SQLException{
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
                User result = new User(
                        (String) user.get("id"),
                        (String) user.get("name"),
                        (String) user.get("email"),
                        (String) user.get("phone"),
                        (String) user.get("role")
                );
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
