package com.tdonuk.passwordmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CryptUtils {
    private static final Base64.Encoder encoder = Base64.getMimeEncoder();
    private static final Base64.Decoder decoder = Base64.getMimeDecoder();

    public static String encode(String raw) throws Exception{
        StringBuilder sb = new StringBuilder(raw);
        salt(sb);
        return encoder.encodeToString(sb.toString().getBytes());
    }

    public static String decode(String encoded) throws IOException {
        StringBuilder sb = new StringBuilder(new String(decoder.decode(encoded)));
        deSalt(sb);
        return sb.toString();
    }

    private static void salt(StringBuilder builder) throws IOException {
        Queue<String> salts = salts();
        while(!salts.isEmpty()) {
            String salt = salts.poll();
            builder.insert(RandomUtils.randomInt(0, builder.length() - 1), salt);
        }
    }
    private static void deSalt(StringBuilder builder) throws IOException {
        Queue<String> salts = salts();
        while(!salts.isEmpty()) {
            String salt = salts.poll();
            if(builder.toString().contains(salt)) {
                builder.delete(builder.toString().indexOf(salt), builder.toString().indexOf(salt) + salt.length());
                continue;
            }
            salts.add(salt);
        }
    }

    private static Queue<String> salts() throws IOException {
        ClassLoader cl = CryptUtils.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream("salt");
        String salts = new String(stream.readAllBytes());
        Queue<String> queue = new ArrayDeque<>(salts.split(" ").length);
        queue.addAll(Arrays.asList(salts.split(" ")));
        return queue;
    }
}
