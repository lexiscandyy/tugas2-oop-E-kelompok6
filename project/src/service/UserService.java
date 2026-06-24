package service;

import model.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserRepository userRepository;

    public void userService(){
        userRepository = new UserRepository();
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public User getUserId(String id){
        return userRepository.getUserId(id);
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
