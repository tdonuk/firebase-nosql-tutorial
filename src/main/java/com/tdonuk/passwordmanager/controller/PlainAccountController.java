package com.tdonuk.passwordmanager.controller;

import com.tdonuk.passwordmanager.domain.dto.PlainAccountDTO;
import com.tdonuk.passwordmanager.service.AccountService;
import com.tdonuk.passwordmanager.service.PlainAccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/plain")
public class PlainAccountController extends UserAccountController<PlainAccountDTO> {

    public PlainAccountController(PlainAccountService accountService) {
        super(accountService);
    }
}
