package com.tdonuk.passwordmanager.domain.dao;

import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BankAccountDAO extends AccountDAO<BankAccount> implements BankAccountRepository {

    @Override
    public List<BankAccount> findByBankName(String name) {
        return null;
    }

    @Override
    public Optional<BankAccount> findByIBAN(String iban) {
        return Optional.empty();
    }
}
