package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.TaskModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface TaskRepository {

    //createTask
    //input:
    // task: Task entity received to be storage
    // token: authentication
    //output:
    // ResponseEntity: With task entity inside
    public ResponseEntity<Object> createTask(TaskModel task, String token);

    //getTask
    //input:
    // task_id: Task entity identification to be retrieved from the storage
    // token: authentication
    //output:
    // ResponseEntity: With task entity inside
    public ResponseEntity<Object> getTask(int task_id, String token );

    //updateTask
    //input:
    // task_id: Task entity identification to be updated of the storage
    // task: Task entity storaging the new attribute to be moddified in the id asked
    // token: authentication
    //output:
    // ResponseEntity: With task entity inside
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token);

    //deleteTask
    //input:
    // task_id: Task entity identification to be deleted of the storage
    // token: authentication
    //output:
    // ResponseEntity: With response from database
    public ResponseEntity<Object> deleteTask(int task_id, String token);

    //getAllTasks
    //input:
    // token: authentication
    //output:
    // ResponseEntity: With List of object from database
    public ResponseEntity<List<Object>> getAllTasks(String token);

    //getTasksByState
    //input:
    //state: state that is being asked to be looked for in the database
    // token: authentication
    //output:
    // ResponseEntity: With List of object from database
    public ResponseEntity<List<Object>> getTasksByState(String state, String token);

    //getTasksByWord
    //input:
    //word: key word that is being asked to be looked for in the database
    // token: authentication
    //output:
    // ResponseEntity: With List of object from database
    public ResponseEntity<List<Object>> getTasksByWord(String word, String token);

    //getTasksByDateExpire
    //input:
    // token: authentication
    //output:
    // ResponseEntity: With List of object from database
    public ResponseEntity<List<Object>> getTasksByDateExpire(String token);

    public ResponseEntity<List<Object>> getTaskByUserId(int user_id, String token);
}
