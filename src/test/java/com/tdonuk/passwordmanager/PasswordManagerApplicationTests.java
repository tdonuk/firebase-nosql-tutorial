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
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.ACCOUNTS;
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.USERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PasswordManagerApplicationTests {
    Firestore dbFirestore;

    static {
        try {
            FirebaseUtils.initApp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private BankAccountDAO bankAccountDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder;

    @BeforeAll
    void contextLoads() throws IOException {
        dbFirestore = FirestoreClient.getFirestore();
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void canReadDocument() throws ExecutionException, InterruptedException, IOException {
        CollectionReference col = dbFirestore.collection("users");


        DocumentReference ref = col.add(new Crud("", "deneme", "test")).get();
        ref.get().get().toObject(Crud.class);

        DocumentReference readRef = col.document(ref.getId());

        System.out.println(readRef.getId());
    }

    @Test
    void canUseTransaction() throws ExecutionException, InterruptedException {
        DocumentReference ref = dbFirestore.collection("cruds").document();
        ApiFuture<String> future =
                dbFirestore.runTransaction(tra -> {
                    Transaction t = tra.set(ref, new Crud(ref.getId(),"deneme", "madenci"));
                    tra.set(ref.collection("tags").document(ref.getId()), new Crud("asdsa", "dsadsa", "bsabsa"), SetOptions.merge());
                    return "Başarılı";
                });
        System.out.println(future.get());
    }

    @Test
    void canSaveEmbeddedCollection() throws Exception {
        UserDTO user = new UserDTO();
        user.setName(new Name("taha", "donuk"));
        user.setUsername("taha");
        user.setEmail("a.a");
        user.setPassword(encoder.encode("1234"));
        user.setPhoneNumber("4321");

        userService.save(user);

        SessionContext.setAttr(LOGGED_USER, new CustomUserDetails(user));
        SessionContext.setAttr(LOGGED_USER_USERNAME, user.getUsername());


        BankAccount account = new BankAccount();
        account.setIban("123123");
        account.setEmail("a.a");
        account.setBankName("iskbank");
        account.setMobileAppPassword("1234");
        account.setCreationDate(new Date());
        account.setOwnerId(user.getUsername());
        account.setAccountNumber("4444");
        account.setType(AccountType.BANK);
        account.setId(account.getIban());

        bankAccountDAO.save(account);

        UserAccount acc = bankAccountDAO.findById(account.getId());
        System.out.println("found: " + acc.toString());
    }

    @Test
    void canEncodeAndDecode() {
        String raw = "abc123";

        String encoded = Base64.getEncoder().encodeToString(raw.getBytes());
        String decoded = new String(Base64.getDecoder().decode(encoded));

        System.out.println(encoded);
        System.out.println(decoded);

        assertEquals(raw, decoded);
    }

    @Test
    void givenWrongId_canNotUpdate() throws Exception {
        CollectionReference accounts = dbFirestore.collection(USERS).document("deneme").collection(ACCOUNTS);

        DocumentReference ref = accounts.document("ads");

        ref.update(Map.of("name", "new")).get().getUpdateTime();
    }
}

@Data
@AllArgsConstructor
class Crud {
    private String documentId;
    private String name;
    private String profession;
}
