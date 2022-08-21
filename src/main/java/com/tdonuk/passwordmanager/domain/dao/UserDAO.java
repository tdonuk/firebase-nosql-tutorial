package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.tdonuk.passwordmanager.domain.Name;
import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import com.tdonuk.passwordmanager.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.USERS;

@Slf4j
@Repository
public class UserDAO implements UserRepository {
    private Firestore firestore;

    @PostConstruct
    public void init() {
        firestore = FirestoreClient.getFirestore();
    }

    @Override
    public UserEntity save(UserEntity entity) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info("Starting save user");

        DocumentReference ref = users.document(entity.getEmail());

        if(ref.get().get().exists()) {
            log.error("User already exists: " + entity.getEmail());
        } else {
            log.info("Nice.");

            entity.setId(ref.getId());

            // save the entity and its collections seperately
            WriteResult result = ref.set(entity).get();

            log.info("New user["+entity.getEmail()+"] Created at " + result.getUpdateTime());
        }
        return entity;
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> entities) {
        return null;
    }

    @Override
    public List<UserEntity> findByField(String field, Object value) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info(String.format("Starting find user by field[%s=%s]", field, value.toString()));

        List<UserEntity> result = users.whereEqualTo(field, value).get().get().toObjects(UserEntity.class);

        return result;
    }

    @Override
    public List<UserEntity> findByName(Name name) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info("Starting find user by name: " + name.toString());

        List<UserEntity> result = users.whereEqualTo("name", name).get().get().toObjects(UserEntity.class);

        return result;
    }

    @Override
    public UserEntity findByEmail(String email) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info("Starting find user by email: " + email);

        DocumentReference ref = users.document(email);

        return Optional.of(ref.get().get().toObject(UserEntity.class)).orElse(null);
    }

    @Override
    public UserEntity findById(String id) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(USERS);

        log.info("Starting find user by id: " + id);

        DocumentReference ref = users.document(id);

        return Optional.of(ref.get().get().toObject(UserEntity.class)).orElse(null);
    }
}
