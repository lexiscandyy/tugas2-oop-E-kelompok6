package model;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;

    public User(String id, String name, String email, String phone, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
