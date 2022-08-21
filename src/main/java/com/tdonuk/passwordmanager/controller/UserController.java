package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UserDTO user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("User has been saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestBody String email) {
        try {
            return ResponseEntity.ok(userService.findByEmail(email));
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
