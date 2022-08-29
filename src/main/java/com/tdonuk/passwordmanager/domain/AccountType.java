package com.tdonuk.passwordmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    GAME("Oyun"), SOCIAL("Sosyal"), PLAIN("Genel"), SHOP("Alışveriş"), BANK("Banka"), GOVERNMENT("Resmi"), CARD("Kart"), OTHER("Diğer");

    private final String label;
}
