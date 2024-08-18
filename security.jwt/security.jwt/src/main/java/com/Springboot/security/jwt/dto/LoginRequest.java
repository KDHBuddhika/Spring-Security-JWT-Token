package com.Springboot.security.jwt.dto;

public class LoginRequest {
    private String userName;
    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
