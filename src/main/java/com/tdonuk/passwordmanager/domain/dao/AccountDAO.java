package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.QueryType;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    protected abstract void encodeEntity(T entity);
    protected  abstract void decodeEntity(T entity);

    @Override
    public T save(T entity) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        log.info("creating account..");

        DocumentReference createdDocument = accounts.document();

        entity.setId(createdDocument.getId());
        entity.setOwner(SessionContext.loggedUsername());
        entity.setCreationDate(new Date());

        encodeEntity(entity);

        WriteResult result = createdDocument.set(entity).get();

        log.info("account created successfully at " + result.getUpdateTime());

        decodeEntity(entity);

        return entity;
    }

    @Override
    public List<T> findAll() throws Exception {
        log.info("find all started to working for user: " + SessionContext.loggedUsername() + " preparing request..");

        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        List<T> results = accounts.orderBy("name").get().get().toObjects(getClassType());

        log.info(results.size() + " " + (BANK_ACCOUNTS.equals(getCollectionName()) ? "Bank Accounts" : "Accounts") + " found for user ["+SessionContext.loggedUsername()+"]");

        log.info("decoding results..");

        results.forEach(result -> decodeEntity(result));

        log.info("findAll successfully fetched user accounts. preparing response..");

        return results;
    }

    @Override
    public T update(String id, Map<String, Object> fields) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        log.info("updating account..");

        DocumentReference ref = accounts.document(id);

        WriteResult result = ref.update(fields).get();

        log.info(String.format("account [%s] is updated successfully ad %s", id, result.getUpdateTime()));

        T entity = ref.get().get().toObject(getClassType());

        decodeEntity(entity);

        return entity;
    }

    @Override
    public T findById(String id) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        DocumentReference ref = accounts.document(id);

        DocumentSnapshot snapshot = ref.get().get();

        log.info(String.format("find by id: %s started working", id));

        if(snapshot.exists()) {
            log.info(String.format("account found with given id. processing..."));

            T entity = snapshot.toObject(getClassType());

            decodeEntity(entity);

            return entity;
        } else return null;
    }

    @Override
    public List<T> findByField(String field, Object value, QueryType type) throws Exception {
        log.info(String.format("findByField started to working with params [field: %s, value: %s, type: %s]", field, value.toString(), type.name()));

        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        List<T> results = new ArrayList<>();

        switch(type) {
            case AFTER:
            case STARTS_WITH:
            case BIGGER_THAN:
                results = accounts.whereGreaterThanOrEqualTo(field, value).get().get().toObjects(getClassType());
                break;
            case BEFORE:
            case ENDS_WITH:
            case SMALLER_THAN:
                results = accounts.whereLessThanOrEqualTo(field, value).get().get().toObjects(getClassType());
                break;
            case CONTAINS:
                // not supported yet
                return new ArrayList<>();
            case EQUALS:
                accounts.whereEqualTo(field, value).get().get().toObjects(getClassType());
                break;
            default:
                return new ArrayList<>();
        }
        results.forEach(this::decodeEntity);
        return results;
    }

    @Override
    public void delete(String id) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(getCollectionName());

        DocumentReference doc = accounts.document(id);

        doc.delete();
    }
}
