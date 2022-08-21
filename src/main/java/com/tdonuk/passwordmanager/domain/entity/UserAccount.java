package com.tdonuk.passwordmanager.domain.entity;

import com.tdonuk.passwordmanager.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    protected String id;

    protected AccountType type;

    protected String name;
    protected String email;
    protected Date creationDate;

    protected String phoneNumber;

    protected String ownerId;
}
