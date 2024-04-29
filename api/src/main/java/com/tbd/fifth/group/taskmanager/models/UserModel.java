package com.tbd.fifth.group.taskmanager.models;

public class UserModel {
    // Atributos
    private int user_id;
    private String name;
    private String email;
    private String password;

    public UserModel() {}

    // UserModel (Constructor)
    //Input:
    // user_id: Identification of the user
    // name: name of the user
    // email: email account of the user
    // password: code for entry to the page of the user
    // OutPut: User entity
    public UserModel(int user_id, String name, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getUser_id() {
        return this.user_id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    // Setters
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
