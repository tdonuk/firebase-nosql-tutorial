package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AccountRepository <T extends UserAccount> {
    T save(T entity) throws ExecutionException, InterruptedException;

    List<T> findByField(String field, Object value);

    List<T> findByAccountType(AccountType type);

    List<T> findByCreationDateAfter(Date startDate);

    List<T> findByOwnerId(String ownerId);

    List<T> findByName(String accountName);

    List<T> findByPhoneNumber();

    T findById(String id) throws ExecutionException, InterruptedException;
}
