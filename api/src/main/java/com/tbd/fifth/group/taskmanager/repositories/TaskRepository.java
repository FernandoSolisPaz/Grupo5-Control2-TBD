package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.TaskModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface TaskRepository {

    //Creamos el CRUD
    public ResponseEntity<Object> createTask(TaskModel task, String token);
    public ResponseEntity<Object> getTask(int task_id, String token );
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token);

    public ResponseEntity<Object> deleteTask(int task_id, String token);

    public ResponseEntity<List<Object>> getAllTasks(String token);
}
