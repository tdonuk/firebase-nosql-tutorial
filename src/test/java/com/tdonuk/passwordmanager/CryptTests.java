package com.tdonuk.passwordmanager;

import com.tdonuk.passwordmanager.util.CryptUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptTests {
    @Test
    void canEncodeAndDecodeWithSalt() throws Exception{
        String password = "bc12f?-_.asd";

        String encoded = CryptUtils.encode(password);
        String decoded = CryptUtils.decode(encoded);

        System.out.println(String.format("raw: %s\tdecoded: %s", password, decoded));

        Assertions.assertEquals(decoded, password);
    }
}
