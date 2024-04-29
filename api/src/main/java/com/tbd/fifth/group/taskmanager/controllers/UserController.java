package com.tbd.fifth.group.taskmanager.controllers;

import com.tbd.fifth.group.taskmanager.models.UserModel;
import com.tbd.fifth.group.taskmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<Object> createUser(@RequestBody UserModel user) {
        return userService.createUser(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserModel user) {
        return userService.loginUser(user.getName(), user.getPassword());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Object> getUser(@PathVariable int user_id) {
        return userService.getUser(user_id);
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int user_id) {
        return userService.deleteUser(user_id);
    }

    @PutMapping("/user")
    public ResponseEntity<Object> updateUser(@RequestBody UserModel user) {
        return userService.updateUser(user);
    }

    @GetMapping("/user/name/{name}")
    public ResponseEntity<Object> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    


}
