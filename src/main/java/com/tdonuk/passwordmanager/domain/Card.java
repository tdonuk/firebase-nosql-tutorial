package com.tdonuk.passwordmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardName;
    private String pin;
    private String expireMonth;
    private String expireYear;
    private String cardNumber;
    private String cvv;
    private String ownerName;
    private CardType cardType;
}
