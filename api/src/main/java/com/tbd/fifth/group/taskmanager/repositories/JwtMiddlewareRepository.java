package com.tbd.fifth.group.taskmanager.repositories;

import com.tbd.fifth.group.taskmanager.models.UserModel;

public interface JwtMiddlewareRepository {

    //generateToken
    //input:
    //userModel: User for authentication
    //output:
    // String: With token
    public String generateToken(UserModel userModel);

    //validateToken
    //input:
    //token: token that will be verified for the authorization
    //output:
    // Boolean: Response for the authentication
    public Boolean validateToken(String token);

    //decodeJWT
    //input:
    //token: token that will be decoded into user info
    //output:
    // UserModel: User info decoded
    public UserModel decodeJWT(String token);
}
