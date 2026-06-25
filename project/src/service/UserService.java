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

    public static void addUser(Map<String, Object> newUser) throws SQLException {

        if(userRepository.findId((String) newUser.get("id")) == true){
            throw new IllegalArgumentException(String.format("User dengan id: %s sudah ada !!", (String) newUser.get("id")));
//            return;
        }

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

    public void updateUser (User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(String id){
        userRepository.deleteUser(id);
    }
}
