package com.tbd.fifth.group.taskmanager.controllers;

import com.tbd.fifth.group.taskmanager.models.TaskModel;
import com.tbd.fifth.group.taskmanager.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public ResponseEntity<Object> createTask(@RequestBody TaskModel task, @RequestParam String token) {
        return taskService.createTask(task, token);
    }

    @GetMapping("/task/{task_id}")
    public ResponseEntity<Object> getTask(@PathVariable int task_id, @RequestParam String token) {
        return taskService.getTask(task_id, token);
    }

    @PutMapping("/task/{task_id}")
    public ResponseEntity<Object> updateTask(@PathVariable int task_id, @RequestBody TaskModel task, @RequestParam String token) {
        return taskService.updateTask(task_id, task, token);
    }

    @DeleteMapping("/task/{task_id}")
    public ResponseEntity<Object> deleteTask(@PathVariable int task_id, @RequestParam String token) {
        return taskService.deleteTask(task_id, token);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Object>> getAllTasks(@RequestParam String token) {
        return taskService.getAllTasks(token);
    }

    @GetMapping("/tasks/{state}")
    public ResponseEntity<List<Object>> getTasksByState(@PathVariable String state, @RequestParam String token) {
        return taskService.getTasksByState(state, token);
    }

    @GetMapping("/tasks/word/{word}")
    public ResponseEntity<List<Object>> getTasksByWord(@PathVariable String word, @RequestParam String token) {
        return taskService.getTasksByWord(word, token);
    }

    @GetMapping("/tasks/date")
    public ResponseEntity<List<Object>> getTasksByDateExpire(@RequestParam String token) {
        return taskService.getTasksByDateExpire(token);
    }

    @GetMapping("/tasks/user/{user_id}")
    public ResponseEntity<List<Object>> getTasksByUserId(@PathVariable int user_id, @RequestParam String token) {
        return taskService.getTaskByUserId(user_id, token);
    }

}
