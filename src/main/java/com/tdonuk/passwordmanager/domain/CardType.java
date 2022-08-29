package com.tdonuk.passwordmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardType {
    DEBIT("Banka Kartı"), CREDIT("Kredi Kartı");

    String label;
}
