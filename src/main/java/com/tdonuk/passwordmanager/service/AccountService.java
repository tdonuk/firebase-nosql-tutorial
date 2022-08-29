package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.AccountType;
import com.tdonuk.passwordmanager.domain.QueryType;
import com.tdonuk.passwordmanager.domain.dao.GenericAccountDAO;
import com.tdonuk.passwordmanager.domain.dto.UserAccountDTO;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class AccountService <T extends UserAccountDTO> {
    @Autowired
    protected BankAccountRepository bankAccountRepository;
    @Autowired
    protected PlainAccountRepository plainAccountRepository;

    @Autowired
    protected GenericAccountDAO genericAccountDAO;

    protected abstract T toDto(UserAccount entity);
    protected abstract List<T> toDto(List<UserAccount> entities);
    protected abstract <Entity extends UserAccount> Entity toEntity(T dto);

    protected abstract AccountRepository getRepository();

    public T save(T dto) throws Exception {
        return toDto(getRepository().save(toEntity(dto)));
    }

    public List<T> findByField(String field, Object value, QueryType type) throws Exception {
        return toDto(getRepository().findByField(field, value, type));
    }

    public List<T> findByField(String field, Object value) throws Exception {
        return findByField(field, value, QueryType.EQUALS);
    }

    public T update(Map<String, Object> fields, String id) throws Exception {
        return toDto(getRepository().update(id, fields));
    }

    public List<T> findByAccountType(AccountType type) throws Exception {
        return findByField("accountType", type.name());
    }

    public List<T> findByCreationDateAfter(Date startDate) throws Exception {
        return findByField("creationDate", startDate, QueryType.AFTER);
    }

    public List<T> findAllByOwner() throws Exception {
        return toDto(getRepository().findAllByOwner());
    }

    public List<T> findByName(String accountName) throws Exception{
        return findByField("name", accountName, QueryType.STARTS_WITH);
    }

    public T findById(String id) throws Exception {
        return toDto(getRepository().findById(id));
    }
}
