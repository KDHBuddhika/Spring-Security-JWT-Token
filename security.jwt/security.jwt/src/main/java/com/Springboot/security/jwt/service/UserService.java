package com.Springboot.security.jwt.service;


import com.Springboot.security.jwt.entity.Role;
import com.Springboot.security.jwt.entity.User;
import com.Springboot.security.jwt.repo.RoleRepo;
import com.Springboot.security.jwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        return userRepo.save(user);
    }

    public void initRoleAndUser(){


          Role adminRole = new Role();
        if(!roleRepo.existsById("Admin")){

            adminRole.setRoleName("Admin");
            adminRole.setRoleDescription("Admin Role");
            roleRepo.save(adminRole);
        }


        Role userRole = new Role();
        if(!roleRepo.existsById("User")){

            userRole.setRoleName("User");
            userRole.setRoleDescription("User Role");
            roleRepo.save(userRole);
        }



        if(!userRepo.existsById("admin@gmail.com")){
            User user = new User();
            user.setUserName("admin@gmail.com");
//            user.setUserPassword(passwordEncoder.encode("1234"));
            user.setUserPassword(getPasswordEncoder("1234"));
            user.setUserFirstName("Dinesh");
            user.setUserSecondName("Hashan");

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            user.setRole(adminRoles);
            userRepo.save(user);
        }

        if(!userRepo.existsById("user@gmail.com")){
            User user = new User();
            user.setUserName("user@gmail.com");
//            user.setUserPassword(passwordEncoder.encode("12345"));
            user.setUserPassword(getPasswordEncoder("12345"));
            user.setUserFirstName("Hashan");
            user.setUserSecondName("Dinesh");

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            user.setRole(userRoles);
            userRepo.save(user);
        }

    }

    public String getPasswordEncoder(String password){
        return passwordEncoder.encode(password);

    }


}
