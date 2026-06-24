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
    public void addUser(User user){}
    public User getUserId(String id){}

    public List<User> getAllUsers(String role) throws SQLException {
        String query;
        if (role == null){
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
                result.add(new User(rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                rs.getString("role")));
            }
        }
        return result;
    }

    public void updateUser(User user){}
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
