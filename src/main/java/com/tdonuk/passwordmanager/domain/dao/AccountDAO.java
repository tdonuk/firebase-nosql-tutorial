package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

public class  AccountDAO <T extends UserAccount> implements AccountRepository<T> {
    protected Firestore firestore;

    @PostConstruct
    public void init() {
        firestore = FirestoreClient.getFirestore();
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return null;
    }

    @Override
    public List<T> findByField(String field, Object value) {
        return null;
    }

    @Override
    public List<T> findByAccountType(AccountType type) {
        return null;
    }

    @Override
    public List<T> findByCreationDateAfter(Date startDate) {
        return null;
    }

    @Override
    public List<T> findByOwnerId(String ownerId) {
        return null;
    }

    @Override
    public List<T> findByName(String accountName) {
        return null;
    }

    @Override
    public List<T> findByPhoneNumber() {
        return null;
    }
}
