package handler;

import server.Request;
import server.Response;
import model.User;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {

    public void getAllUsers(Request req, Response res) throws Exception{
        String role = req.getQueryParam("role");
        List<User> data = new ArrayList<>();
        data = UserService.getAllUsers(role);
        res.sendSuccess(data);
    }

    public void getUserById(Request req, Response res) throws Exception{
        String id =req.getPathParam("id");
        User data = UserService.getUserById(id);
        res.sendSuccess(data);
    }

}
