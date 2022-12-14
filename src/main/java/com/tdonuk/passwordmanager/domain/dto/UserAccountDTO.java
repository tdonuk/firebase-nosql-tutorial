package com.tdonuk.passwordmanager.domain.dto;

import com.tdonuk.passwordmanager.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
    protected String id;
    protected String name;
    protected String email;
    protected Date creationDate;
    protected String phoneNumber;
    protected String owner;
}
