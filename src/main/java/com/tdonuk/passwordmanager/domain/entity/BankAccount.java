package com.tdonuk.passwordmanager.domain.entity;

import com.tdonuk.passwordmanager.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount extends UserAccount{
    private String bankName;
    private String mobileAppPassword;
    private String iban;
    private String accountNumber;
    private List<Card> cards;
}
