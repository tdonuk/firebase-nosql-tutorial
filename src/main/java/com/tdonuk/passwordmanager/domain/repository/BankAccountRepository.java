package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends AccountRepository<BankAccount>{
    List<BankAccount> findByBankName(String name);

    Optional<BankAccount> findByIBAN(String iban);
}
