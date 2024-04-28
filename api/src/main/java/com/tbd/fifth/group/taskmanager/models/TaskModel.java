package com.tbd.fifth.group.taskmanager.models;

import java.util.Date;

public class TaskModel {
    // Atributos
    private int task_id;
    private int user_id;
    private String title;
    private String description;
    private String date_of_expire;
    private String state;

    // Constructor por defecto
    public TaskModel() {
    }

    //
    public TaskModel(int task_id, int user_id, String title, String description, String date_of_expire, String state) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.date_of_expire = date_of_expire;
        this.state = state;
    }

    // Getters
    public int getTask_id() {
        return this.task_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate_of_expire() {
        return this.date_of_expire;
    }

    public String getState() {
        return this.state;
    }

    // Setters
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_of_expire(String date_of_expire) {
        this.date_of_expire = date_of_expire;
    }

    public void setState(String state) {
        this.state = state;
    }
}
