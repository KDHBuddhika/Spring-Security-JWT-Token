package com.Springboot.security.jwt.dto;

import com.Springboot.security.jwt.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponse {

    private User user;
    private String jwtToken;
}
