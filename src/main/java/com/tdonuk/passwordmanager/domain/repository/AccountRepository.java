package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.QueryType;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface AccountRepository <T extends UserAccount> {
    T save(T entity) throws Exception;

    T update(String id, Map<String, Object> fields) throws Exception;

    T findById(String id) throws Exception;

    List<T> findAll() throws Exception;

    List<T> findByField(String field, Object value, QueryType type) throws Exception;

    void delete(String id) throws Exception;
}
