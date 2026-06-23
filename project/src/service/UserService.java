package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public userService(){
        userRepository = new UserRepository();
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public User getUserId(String id){
        return userRepository.getUserId(id);
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public void updateUser (User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(String id){
        userRepository.deleteUser(id);
    }
}
