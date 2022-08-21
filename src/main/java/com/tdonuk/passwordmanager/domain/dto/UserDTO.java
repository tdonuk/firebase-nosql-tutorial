package com.tdonuk.passwordmanager.domain.dto;

import com.tdonuk.passwordmanager.domain.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

