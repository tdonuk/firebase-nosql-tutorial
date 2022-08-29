package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.dto.BankAccountDTO;
import com.tdonuk.passwordmanager.service.AccountService;
import com.tdonuk.passwordmanager.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts/bank")
public class BankAccountController extends UserAccountController<BankAccountDTO> {
    public BankAccountController(BankAccountService accountService) {
        super(accountService);
    }

    @GetMapping("/i/{iban}")
    public ResponseEntity<?> findByIBAN(@PathVariable String iban) {
        try {
            return ResponseEntity.ok(((BankAccountService) accountService).findByIban(iban));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
