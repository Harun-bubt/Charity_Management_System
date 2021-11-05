package com.example.charitymanagementsystem;

public class Model{
    //model class it has an empty constructor
    // and another constructor and getter and setter methods
    public Model(){
    }
    String Email;
    String username;
    String status;
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Model(String type, String id) {
        this.type = type;
        this.id = id;
    }

    String id;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Model(String email, String username, String status){
        Email = email;
        this.username = username;
        this.status = status;
    }
}
