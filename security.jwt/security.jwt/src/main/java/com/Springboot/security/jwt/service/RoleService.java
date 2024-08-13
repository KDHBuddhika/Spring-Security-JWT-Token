package com.Springboot.security.jwt.service;


import com.Springboot.security.jwt.entity.Role;
import com.Springboot.security.jwt.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {


    @Autowired
    private RoleRepo roleRepo;

    public Role createNewRole(Role role) {
        return roleRepo.save(role);
    }
}
