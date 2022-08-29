package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public interface BankAccountRepository extends AccountRepository<BankAccount>{
    BankAccount findByIBAN(String iban) throws Exception;
}
