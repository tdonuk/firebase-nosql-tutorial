package com.tdonuk.passwordmanager;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.Name;
import com.tdonuk.passwordmanager.domain.dao.BankAccountDAO;
import com.tdonuk.passwordmanager.domain.dao.UserDAO;
import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.security.domain.CustomUserDetails;
import com.tdonuk.passwordmanager.service.UserService;
import com.tdonuk.passwordmanager.util.FirebaseUtils;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER;
import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER_USERNAME;
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PasswordManagerApplicationTests {

    @Test
    void canEncodeAndDecode() {
        String raw = "abc123";

        String encoded = Base64.getEncoder().encodeToString(raw.getBytes());
        String decoded = new String(Base64.getDecoder().decode(encoded));

        System.out.println(encoded);
        System.out.println(decoded);

        assertEquals(raw, decoded);
    }

}
