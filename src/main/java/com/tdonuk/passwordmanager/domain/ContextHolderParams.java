package com.tdonuk.passwordmanager.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextHolderParams {
    public static final String LOGGED_USER = "loggedUser";
    public static final String LOGGED_USER_USERNAME = "loggedUserUsername";
}
