package com.tdonuk.passwordmanager.domain.dao;

import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.entity.PlainAccount;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.tdonuk.passwordmanager.util.CryptUtils.decode;
import static com.tdonuk.passwordmanager.util.CryptUtils.encode;

@Repository
public class PlainAccountDAO extends AccountDAO<PlainAccount> implements PlainAccountRepository {

    @Override
    protected String getCollectionName() {
        return FirestoreCollections.PLAIN_ACCOUNTS;
    }

    @Override
    protected Class<PlainAccount> getClassType() {
        return PlainAccount.class;
    }

    @SneakyThrows
    @Override
    protected void encodeEntity(PlainAccount entity) {
        entity.setPassword(encode(entity.getPassword()));
        entity.setEmail(encode(entity.getEmail()));
        if(StringUtils.isNotBlank(entity.getPhoneNumber())) entity.setPhoneNumber(encode(entity.getPhoneNumber()));
    }

    @SneakyThrows
    @Override
    protected void decodeEntity(PlainAccount entity) {
        entity.setPassword(decode(entity.getPassword()));
        entity.setEmail(decode(entity.getEmail()));
        if(StringUtils.isNotBlank(entity.getPhoneNumber())) entity.setPhoneNumber(decode(entity.getPhoneNumber()));

    }
}
