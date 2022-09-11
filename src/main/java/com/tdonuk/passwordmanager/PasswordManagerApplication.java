package com.tdonuk.passwordmanager;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.tdonuk.passwordmanager.util.FirebaseUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class PasswordManagerApplication {

    public static void main(String[] args) throws IOException {
        FirebaseUtils.initApp();
        SpringApplication.run(PasswordManagerApplication.class, args);
    }

}
