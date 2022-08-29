package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.Name;
import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public interface UserRepository {
    UserEntity save(UserEntity entity) throws Exception;

    UserEntity update(Map<String, Object> newFields) throws Exception;

    List<UserEntity> saveAll(List<UserEntity> entities);

    List<UserEntity> findByField(String field, Object value) throws ExecutionException, InterruptedException;

    List<UserEntity> findByName(Name name) throws ExecutionException, InterruptedException;

    UserEntity findByEmail(String email) throws ExecutionException, InterruptedException;

    UserEntity findById(String id) throws ExecutionException, InterruptedException;
}
