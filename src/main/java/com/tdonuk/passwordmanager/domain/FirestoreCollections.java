package com.tdonuk.passwordmanager.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
/**
 * Holds firestore collection names
 */
public final class FirestoreCollections {
    public static final String USERS = "users";
    public static final String ACCOUNTS = "accounts";
    public static final String BANK_ACCOUNTS = "bank_accounts";
    public static final String PLAIN_ACCOUNTS = "plain_accounts";
    public static final String DEBIT_CARDS = "debit_cards";
}
