package com.tdonuk.passwordmanager.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.service.UserService;
import com.tdonuk.passwordmanager.util.JWTUtils;
import com.tdonuk.passwordmanager.util.SessionContext;
import com.tdonuk.passwordmanager.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody UserDTO user) {
        try {
            ValidationUtils.validateUser(user);

            userService.save(user);

            return ResponseEntity.ok("User has created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> save(@RequestBody HashMap<String, Object> fields) {
        try {
            ValidationUtils.validateFields(fields);

            userService.update(fields);

            return ResponseEntity.ok("User has created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = tokenHeader.substring("Bearer ".length());

                Algorithm alg = Algorithm.HMAC256("secret");
                JWTVerifier verifier = JWT.require(alg).build();

                DecodedJWT decoded = verifier.verify(refreshToken);

                String username = decoded.getSubject();

                UserDTO user = userService.findById(username);

                String accessToken = JWTUtils.createDefault(username, List.of());

                return ResponseEntity.ok(accessToken);

            } catch (TokenExpiredException e) {
                return ResponseEntity.badRequest().body("Maximum oturum süreniz dolmuştur. Lütfen yeniden giriş yapınız.");
            } catch (Exception e) {
                return ResponseEntity.status(401).body("Lütfen giriş yapınız.");
            }
        }
        return ResponseEntity.status(401).body("Lütfen giriş yapınız.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        try {
            UserDTO user = SessionContext.loggedUser();
            if(Objects.isNull(user)) {
                if(StringUtils.isNotBlank(SessionContext.loggedUsername())) {
                    user = userService.findById(SessionContext.loggedUsername());
                }
                else throw new Exception("Please login to continue");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUser(@RequestParam String property, @RequestParam String value) {
        try {
            return ResponseEntity.ok(userService.findByField(property, value));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
