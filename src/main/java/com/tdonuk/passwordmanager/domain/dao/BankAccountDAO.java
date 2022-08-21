package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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

    @Override
    protected String getCollectionName() {
        return FirestoreCollections.BANK_ACCOUNTS;
    }

    @Override
    protected Class<BankAccount> getClassType() {
        return BankAccount.class;
    }

}
