package service;

import model.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserService {
    private static UserRepository userRepository;

    public void userService(){
        userRepository = new UserRepository();
    }

    public static String generateId() throws SQLException{
        String result = "USR-";
        result += String.valueOf(userRepository.countUser() + 1);
        return result;
    }

    public static void addUser(Map<String, Object> newUser) throws SQLException {
        newUser.put("id", generateId());

        if(userRepository.findEmail((String) newUser.get("email")) == true){
            throw new IllegalArgumentException("email sudah ada !!");
//            return;
        }

        if(userRepository.findPhone((String) newUser.get("phone")) == true){
            throw new IllegalArgumentException("Nomor hp sudah dipakai !!");
//            return;
        }

        userRepository.addUser(newUser);
    }

    public static User getUserById(String id) throws SQLException {
        return userRepository.getUserById(id);
    }

    public static List<User> getAllUsers(String role) throws SQLException {
        return userRepository.getAllUsers(role);
    }

    public static User updateUser (Map<String, Object> user) throws SQLException {
        if(userRepository.findId((String) user.get("id")) == false){
            throw new SQLException("ID tidak ditemukan");
        }

        if((String)user.get("email") != null && userRepository.findEmail((String)user.get("email")) == true){
            throw new IllegalArgumentException(String.format("Email %s sudah terdaftar pada user lain", (String) user.get("email")));
        }

        if((String)user.get("phone") != null && userRepository.findPhone((String)user.get("phone")) == true){
            throw new IllegalArgumentException(String.format("nomor %s sudah terdaftar pada user lain", (String) user.get("phone")));
        }
        return userRepository.updateUser(user);
    }
//
//    public void deleteUser(String id){
//        userRepository.deleteUser(id);
//    }
}
