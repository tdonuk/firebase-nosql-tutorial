package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.dto.PlainAccountDTO;
import com.tdonuk.passwordmanager.service.PlainAccountService;
import com.tdonuk.passwordmanager.util.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController extends BaseAccountController<PlainAccountDTO> {

    public AccountController(PlainAccountService accountService) {
        super(accountService);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(accountService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
