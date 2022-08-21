package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.dto.UserAccountDTO;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class AccountService <T extends UserAccountDTO>{
    @Autowired
    protected BankAccountRepository bankAccountRepository;
    @Autowired
    protected PlainAccountRepository plainAccountRepository;

    public T save(T dto) {
        return null;
    }

    public List<T> saveAll(List<T> entities) {
        return null;
    }

    public List<T> findByField(String field, Object value) {
        return null;
    }

    public List<T> findByAccountType(AccountType type) {
        return null;
    }

    public List<T> findByCreationDateAfter(Date startDate) {
        return null;
    }

    public List<T> findByOwnerId(String ownerId) {
        return null;
    }

    public List<T> findByName(String accountName) {
        return null;
    }

    public List<T> findByPhoneNumber() {
        return null;
    }
}
