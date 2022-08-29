package com.tdonuk.passwordmanager.domain.dto;

import com.tdonuk.passwordmanager.domain.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private Name name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date creationDate;
    private Date lastLogin;
}

