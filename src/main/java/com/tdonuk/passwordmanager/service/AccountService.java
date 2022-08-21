package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.dto.UserAccountDTO;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class AccountService <T extends UserAccountDTO> {
    @Autowired
    protected BankAccountRepository bankAccountRepository;
    @Autowired
    protected PlainAccountRepository plainAccountRepository;

    protected abstract T toDto(UserAccount entity);
    protected abstract List<T> toDto(List<UserAccount> entities);
    protected abstract <Entity extends UserAccount> Entity toEntity(T dto);

    protected abstract AccountRepository getRepository();

    public T save(T dto) throws ExecutionException, InterruptedException {
        return toDto(getRepository().save(toEntity(dto)));
    }

    public List<T> findByField(String field, Object value) {
        return toDto(getRepository().findByField(field, value));
    }

    public List<T> findByAccountType(AccountType type) {
        return findByField("accountType", type.name());
    }

    public List<T> findByCreationDateAfter(Date startDate) { // TODO
        return null;
    }

    public List<T> findByOwnerId(String ownerId) {
        return findByField("ownerId", ownerId);
    }

    public List<T> findByName(String accountName) {
        return findByField("name", accountName);
    }

    public List<T> findByPhoneNumber(String phone) {
        return findByField("phoneNumber", phone);
    }

    public T findById(String id) throws ExecutionException, InterruptedException {
        return toDto(getRepository().findById(id));
    }
}
