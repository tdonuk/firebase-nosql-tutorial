package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.Card;
import com.tdonuk.passwordmanager.domain.dto.BankAccountDTO;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.service.BankAccountService;
import com.tdonuk.passwordmanager.util.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts/bank")
public class BankAccountController extends BaseAccountController<BankAccountDTO> {
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

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(accountService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cards")
    public ResponseEntity<?> addCard(@PathVariable String id, @RequestBody Card card) {
        try {
            ((BankAccountService)accountService).addCard(id, card);
            return ResponseEntity.ok("Transaction completed successfully.");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
