package com.tdonuk.passwordmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtils {
    private static SecureRandom random = new SecureRandom();

    public static String randomText(int len) {
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return new String(bytes);
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max-min) + min;
    }
}
