package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserRepository {

    public ResponseEntity<Object> createUser(UserModel user);

    public ResponseEntity<Object> getUser(int user_id);

    public ResponseEntity<Object> deleteUser(int user_id);

    public ResponseEntity<Object> updateUser(UserModel user);

    public ResponseEntity<Object> getUserByEmail(String email);

    public ResponseEntity<Object> loginUser(String email, String password);

    public ResponseEntity<Object> getUserByName(String name);
}
