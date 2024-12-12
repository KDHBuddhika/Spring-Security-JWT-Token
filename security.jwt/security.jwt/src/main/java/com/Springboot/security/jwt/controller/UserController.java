package com.Springboot.security.jwt.controller;


import com.Springboot.security.jwt.entity.User;
import com.Springboot.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping({"register-new-user"})
    public User registerNewUser(@RequestBody User user)
    {

        return userService.registerNewUser(user);
    }

    @PostConstruct                //auto me method ekh wade karanwa api run karapu gaman
    public void iniRoleAndUser(){
        userService.initRoleAndUser();
    }


    @GetMapping({"for-admin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this url is only accesible for Admin";
    }


    @GetMapping({"for-user"})
    @PreAuthorize("hasAnyRole('User','Admin')")
    public String forUser(){
        return "this url is only accesible for User";
    }




}
