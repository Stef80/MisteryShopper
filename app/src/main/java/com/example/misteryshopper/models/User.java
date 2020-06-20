package com.example.misteryshopper.models;

public class User {

    String email;
    String id;
    String role;


    public  User(){}
    public User(String email, String id,String role) {
        this.email = email;
        this.id = id;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
