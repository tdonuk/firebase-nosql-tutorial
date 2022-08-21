package com.tdonuk.passwordmanager.domain;

public enum AccountType {
    GAME("Oyun"), SOCIAL("Sosyal"), SHOP("Alışveriş"), BANK("Banka"), GOVERNMENT("Resmi"), CARD("Kart"), OTHER("Diğer");

    private final String label;
    AccountType(String label) {
        this.label = label;
    }
}
