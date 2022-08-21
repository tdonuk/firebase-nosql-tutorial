package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.entity.PlainAccount;
import com.tdonuk.passwordmanager.service.AccountService;
import com.tdonuk.passwordmanager.service.BankAccountService;
import com.tdonuk.passwordmanager.service.PlainAccountService;
import com.tdonuk.passwordmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/accounts/")
public class AccountController {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private PlainAccountService plainAccountService;
    @Autowired
    private UserService userService;

    @GetMapping("plain/{id}")
    public ResponseEntity<?> getPlainAccountDetails(@PathVariable String id) {
        try {
            return ResponseEntity.ok(plainAccountService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("bank/{id}")
    public ResponseEntity<?> getBankAccountDetails(@PathVariable String id) {
        try {
            return ResponseEntity.ok(bankAccountService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
