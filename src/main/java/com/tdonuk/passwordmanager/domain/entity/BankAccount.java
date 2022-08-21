package com.tdonuk.passwordmanager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount extends UserAccount{
    private String bankName;
    private String mobileAppPassword;
    private String iban;
    private String accountNumber;
}
