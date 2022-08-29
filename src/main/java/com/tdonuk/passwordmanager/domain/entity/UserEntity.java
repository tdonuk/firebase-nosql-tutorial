package com.tdonuk.passwordmanager.domain.entity;

import com.tdonuk.passwordmanager.domain.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String id;
    private Name name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date creationDate;
    private Date lastLogin;
}

