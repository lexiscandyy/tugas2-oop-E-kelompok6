package handler;

import server.Request;
import server.Response;
import model.User;
import service.UserService;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(data == null){
            res.sendError(404, "Id tidak ditemukan");
            return;
        }
        res.sendSuccess(data);
    }

    public void addUser(Request req, Response res) throws Exception{
        Map<String, Object> body = req.getJSON();
        if (body == null) {
            res.sendError(400, "Request body harus berformat JSON (Content-Type: application/json)");
            return;
        }

        if((String)body.get("name") == null || ((String)body.get("name")).isEmpty()){
            res.sendError(400, "name tidak boleh kosong");
            return;
        }
        if((String)body.get("email") == null || ((String)body.get("email")).isEmpty()){
            res.sendError(400, "email tidak boleh kosong");
            return;
        }
        if((String)body.get("phone") == null || ((String)body.get("phone")).isEmpty()){
            res.sendError(400, "phone tidak boleh kosong");
            return;
        }
        if((String)body.get("role") == null || ((String)body.get("role")).isEmpty()){
            res.sendError(400, "role tidak boleh kosong");
            return;
        }

        if(((String) body.get("role")).equals("buyer") == false && ((String) body.get("role")).equals("organizer") == false){
            res.sendError(400, "role harus 'buyer' atau 'organizer'");
            return;
        }

        UserService.addUser(body);
        // Proses data...
        Map<String, Object> created = new HashMap<>();
        created.put("id", body.get("id"));
        created.put("name", body.get("name"));
        created.put("email", body.get("email"));
        created.put("phone", body.get("phone"));
        created.put("role", body.get("role"));

        created.put("message", "Data berhasil dibuat");
        res.sendCreated(created);
    }

    public void updateUser(Request req, Response res) throws Exception{
        Map<String, Object> body = req.getJSON();
        if (body == null) {
            res.sendError(400, "Request body harus berformat JSON");
            return;
        }

        if(body.get("id") == null || ((String)body.get("id")).isEmpty()){
            res.sendError(400, "ID user kosong");
            return ;
        }

        if(body.get("name") == null
                && body.get("email") == null
                && body.get("phone") == null
                && body.get("role") == null){
            res.sendError(400, "Tidak ada data yang mau di update");
            return;
        }

        if(body.get("name") != null && ((String)body.get("name")).isEmpty()){
            res.sendError(400, "nama user kosong");
            return ;
        }

        User result = UserService.updateUser(body);
        if(result == null){
            res.sendError(404, "id tidak ditemukan");
            return;
        }
        res.sendSuccess("Successfully updated !");
    }

}
