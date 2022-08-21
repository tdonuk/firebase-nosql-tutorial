package com.tdonuk.passwordmanager.domain.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public abstract class AccountDAO <T extends UserAccount> implements AccountRepository<T> {
    protected Firestore firestore;

    @PostConstruct
    public void init() {
        firestore = FirestoreClient.getFirestore();
    }

    protected abstract String getCollectionName();
    protected abstract Class<T> getClassType();

    @Override
    public T save(T entity) throws ExecutionException, InterruptedException {
        CollectionReference accounts = firestore.collection(getCollectionName());

        DocumentReference ref = accounts.document(entity.getId());

        if(ref.get().get().exists()) {
            log.info(String.format("account %s already exists. trying to update..", ref.getId()));

            ref.set(entity, SetOptions.merge());
        } else {
            log.info("account not exists. creating..");

            DocumentReference createdDocument = accounts.document();

            entity.setId(createdDocument.getId());

            createdDocument.set(entity);
        }
        return entity;
    }

    @Override
    public T findById(String id) throws ExecutionException, InterruptedException {
        CollectionReference accounts = firestore.collection(getCollectionName());

        DocumentReference ref = accounts.document(id);

        DocumentSnapshot snapshot = ref.get().get();

        if(snapshot.exists()) {
            return snapshot.toObject(getClassType());
        } else return null;
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
