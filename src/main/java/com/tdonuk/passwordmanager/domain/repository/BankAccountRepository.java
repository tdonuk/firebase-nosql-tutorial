package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.entity.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends AccountRepository<BankAccount>{
    List<BankAccount> findByBankName(String name);

    Optional<BankAccount> findByIBAN(String iban);
}
