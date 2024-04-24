package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.UserModel;

public interface JwtMiddlewareRepository {

    public String generateToken(UserModel userModel);

    public Boolean validateToken(String token);
}
