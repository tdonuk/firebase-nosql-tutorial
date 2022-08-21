package com.tdonuk.passwordmanager;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class PasswordManagerApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader cl = PasswordManagerApplication.class.getClassLoader();

        InputStream serviceAccountStream = cl.getResourceAsStream("password-manager-22b1a-firebase-adminsdk-j6cub-30e80c2ebb.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(PasswordManagerApplication.class, args);
    }

}
