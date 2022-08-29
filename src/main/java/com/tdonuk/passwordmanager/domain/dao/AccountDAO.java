package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.QueryType;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.*;

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
    public T save(T entity) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(ACCOUNTS);

        log.info("creating account..");

        DocumentReference createdDocument = accounts.document();

        entity.setId(createdDocument.getId());
        entity.setOwner(SessionContext.loggedUsername());
        entity.setCreationDate(new Date());

        createdDocument.set(entity);

        log.info("account created successfully");

        return entity;
    }

    @Override
    public T update(String id, Map<String, Object> fields) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(ACCOUNTS);

        log.info("updating account..");

        DocumentReference ref = accounts.document(id);

        ref.update(fields).get();

        log.info(String.format("account[%s] is updated successfully", id));

        return ref.get().get().toObject(getClassType());
    }

    @Override
    public T findById(String id) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(ACCOUNTS);

        DocumentReference ref = accounts.document(id);

        DocumentSnapshot snapshot = ref.get().get();

        log.info(String.format("Find by id: %s started working", id));

        if(snapshot.exists()) {
            log.info(String.format("Account found with given id. Processing..."));
            return snapshot.toObject(getClassType());
        } else return null;
    }

    @Override
    public List<T> findByField(String field, Object value, QueryType type) throws Exception {
        log.info(String.format("findByField started to working with params [field: %s, value: %s, type: %s]", field, value.toString(), type.name()));

        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(ACCOUNTS);

        switch(type) {
            case AFTER:
            case STARTS_WITH:
            case BIGGER_THAN:
                return accounts.whereGreaterThanOrEqualTo(field, value).get().get().toObjects(getClassType());
            case BEFORE:
            case ENDS_WITH:
            case SMALLER_THAN:
                return accounts.whereLessThanOrEqualTo(field, value).get().get().toObjects(getClassType());
            case CONTAINS:
                return new ArrayList<>();
            case EQUALS:
                return accounts.whereEqualTo(field, value).get().get().toObjects(getClassType());
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<T> findAllByOwner() throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document((SessionContext.loggedUsername())).collection(ACCOUNTS);

        log.info("findAllByOwner started working..");

        return accounts.whereEqualTo("ownerId", SessionContext.loggedUsername()).get().get().toObjects(getClassType());
    }
}
