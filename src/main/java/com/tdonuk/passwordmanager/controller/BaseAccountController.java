package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.dto.UserAccountDTO;
import com.tdonuk.passwordmanager.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public abstract class BaseAccountController<T extends UserAccountDTO> {
    protected final AccountService<T> accountService;

    public BaseAccountController(AccountService<T> accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody T account) {
        try {
            return ResponseEntity.ok(accountService.save(account));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> fields) {
        try {
            return ResponseEntity.ok(accountService.update(fields, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable String id) {
        try {
            return ResponseEntity.ok(accountService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            accountService.delete(id);
            return ResponseEntity.ok("Account " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
