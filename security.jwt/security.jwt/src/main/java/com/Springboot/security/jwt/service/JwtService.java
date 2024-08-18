package com.Springboot.security.jwt.service;


import com.Springboot.security.jwt.dto.LoginRequest;
import com.Springboot.security.jwt.dto.LoginResponse;
import com.Springboot.security.jwt.entity.Role;
import com.Springboot.security.jwt.entity.User;
import com.Springboot.security.jwt.repo.UserRepo;
import com.Springboot.security.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).get();

        if (user != null ){
            return  new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        }else{
            throw new UsernameNotFoundException("User not found with user name " +username);
        }

    }

    private Set getAuthority(User user)
    {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

//        for(Role role : user.getRole())
//        {
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getRoleName() ));
//        }

        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()));
        });

        return authorities;


    }

//-----------------------------------------------------------------------------------------
    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUserName();
        String userPassword = loginRequest.getUserPassword();

        authenticate(username,userPassword);

        UserDetails userDetails = loadUserByUsername(username);
        String newGenerateToken = jwtUtil.createToken(userDetails);      //user details walin tiken ekk generate kirima
        User user =  userRepo.findById(username).get();

        LoginResponse loginResponse = new LoginResponse(
                user,
                newGenerateToken
        );


   return loginResponse;

    }

 //-------------------------------------------------------------------------------------
    public void authenticate(String username,String userPassword) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userPassword));

        }catch (BadCredentialsException e){
            throw new Exception("Invalid_credentials");

        }
    }
}
