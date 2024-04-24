package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserRepository {

    public ResponseEntity<Object> createUser(UserModel user);

    public UserModel getUser(int user_id);

    public UserModel deleteUser(int user_id);

    public UserModel updateUser(UserModel user);

    public UserModel getUserByEmail(String email);

    public ResponseEntity<Object> loginUser(String email, String password);
}
