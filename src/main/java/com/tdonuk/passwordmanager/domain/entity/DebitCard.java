package com.tdonuk.passwordmanager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    private String pin;
    private Date expirationDate;
    private String cardNumber;
    private String cvv;
    private BankAccount bankAccount;
}
