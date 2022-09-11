package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.Card;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends AccountRepository<BankAccount>{
    BankAccount findByIBAN(String iban) throws Exception;

    void addCard(String id, Card card) throws Exception;
}
