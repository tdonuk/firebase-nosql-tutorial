package com.tdonuk.passwordmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CryptUtils {
    public static String encode(String raw) {
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    public static String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }
}
