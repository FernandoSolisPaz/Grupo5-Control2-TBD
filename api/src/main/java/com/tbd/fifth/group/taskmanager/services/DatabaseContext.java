package com.tbd.fifth.group.taskmanager.services;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

import javax.crypto.SecretKey;

@Configuration
public class DatabaseContext {
    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.user}")
    private String dbUser;

    @Value("${database.password}")
    private String dbPass;

    @Bean
    public Sql2o sql2o(){
        return new Sql2o(dbUrl, dbUser, dbPass);
    }

    @Bean
    public SecretKey secretKey() {
        return Jwts.SIG.HS256.key().build();
    }



}
