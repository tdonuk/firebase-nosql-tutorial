package com.tdonuk.passwordmanager;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PasswordManagerApplicationTests {
    ClassLoader cl;
    Firestore dbFirestore;

    @BeforeAll
    void contextLoads() throws IOException {
        cl = PasswordManagerApplication.class.getClassLoader();

        InputStream serviceAccountStream = cl.getResourceAsStream("password-manager-22b1a-firebase-adminsdk-j6cub-30e80c2ebb.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.initializeApp(options);

        dbFirestore = FirestoreClient.getFirestore();
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


}

@Data
@AllArgsConstructor
class Crud {
    private String documentId;
    private String name;
    private String profession;

}
