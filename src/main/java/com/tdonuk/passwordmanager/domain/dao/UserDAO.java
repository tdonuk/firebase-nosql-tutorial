package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import com.tdonuk.passwordmanager.domain.repository.UserRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER;
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.USERS;

@Slf4j
@Repository
public class UserDAO implements UserRepository {
    private Firestore firestore;
    BCryptPasswordEncoder encoder;

    @PostConstruct
    public void init() {
        firestore = FirestoreClient.getFirestore();
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserEntity save(UserEntity entity) throws Exception {
        CollectionReference users = firestore.collection(USERS);

        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setId(entity.getUsername()); // we are using username as id

        log.info("saveUser started to work with username: " + entity.getUsername());

        DocumentReference ref = users.document(entity.getId());

        if(ref.get().get().exists()) {
            log.error(String.format("a user found with given username [%s]. duplicate not allowed, canceling the process..", entity.getUsername()));
            throw new Exception("User already exists with given ID. To update properties of this user, login first and then send a PUT request with fields to update");
        } else {
            log.info(String.format("user does not exist with given username [%s], trying to create..", entity.getUsername()));

            entity.setCreationDate(new Date());

            WriteResult result = ref.set(entity).get();

            log.info("new user with username ["+entity.getUsername()+"] is created at" + result.getUpdateTime());
        }
        return entity;
    }

    @Override
    public UserEntity update(Map<String, Object> newFields) throws Exception {
        CollectionReference users = firestore.collection(USERS);
        DocumentReference userRef = users.document(SessionContext.loggedUsername());

        log.info(String.format("Updating user [%s]...", SessionContext.loggedUsername()));
        try {
            WriteResult result = userRef.update(newFields).get();

            log.info(String.format("User [%s] is updated at %s", SessionContext.loggedUsername(), result.getUpdateTime()));

            UserEntity updatedUser = findById(SessionContext.loggedUsername());

            SessionContext.setAttr(LOGGED_USER, updatedUser);

            return updatedUser;

        } catch(Exception e) {
            throw new Exception("An unknown error happened. Please re-login and try again");
        }
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserEntity> findByField(String field, Object value) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info(String.format("starting find user by field [%s=%s]", field, value.toString()));

        List<UserEntity> result = users.whereEqualTo(field, value).get().get().toObjects(UserEntity.class);

        if(Objects.isNull(result) || result.isEmpty()) return new ArrayList<>();

        //process results

        return result;
    }

    @Override
    public UserEntity findById(String id) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info("Starting find user by id: " + id);

        DocumentReference ref = users.document(id);

        UserEntity result = ref.get().get().toObject(UserEntity.class);

        if(Objects.isNull(result)) return null;

        // process the fetched result

        return result;
    }
}
