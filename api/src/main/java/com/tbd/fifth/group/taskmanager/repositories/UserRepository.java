package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserRepository {

    //createUser
    //input:
    //user: user entity that is going to be stored in database
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> createUser(UserModel user);

    //getUser
    //input:
    //user_id: identification key  that being asked the database to retrieve a user info
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> getUser(int user_id);

    //getUser
    //input:
    //user_id: identification key  that being asked the database to delete
    //output:
    // ResponseEntity: With answer from database
    public ResponseEntity<Object> deleteUser(int user_id);

    //updateUser
    //input:
    //user: user entity that being asked the database to update with his info
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> updateUser(UserModel user);

    //getUserByEmail
    //input:
    //email: email that is being asked the database for search of a user info
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> getUserByEmail(String email);

    //loginUser
    //input:
    //email: email for authentication
    //password: For authentication
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> loginUser(String email, String password);

    //getUserByName
    //input:
    //name: name that is asked the database to retrieve user info
    //output:
    // ResponseEntity: With object from database
    public ResponseEntity<Object> getUserByName(String name);
}
