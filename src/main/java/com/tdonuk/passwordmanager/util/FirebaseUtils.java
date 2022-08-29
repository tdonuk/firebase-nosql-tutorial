package com.tdonuk.passwordmanager.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.PasswordManagerApplication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FirebaseUtils {
    public static void initApp() throws IOException {
        ClassLoader cl;

        cl = PasswordManagerApplication.class.getClassLoader();

        InputStream serviceAccountStream = cl.getResourceAsStream("password-manager-22b1a-firebase-adminsdk-j6cub-30e80c2ebb.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
