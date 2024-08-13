package com.Springboot.security.jwt.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    private String userName;

    private String userFirstName;
    private String userSecondName;
    private String userPassword;
    //  many to many relationship have among user and role

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
    joinColumns = {
            @JoinColumn(name = "USER_ID")
    },
     inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
     }
    )
    private Set<Role> role;


}
