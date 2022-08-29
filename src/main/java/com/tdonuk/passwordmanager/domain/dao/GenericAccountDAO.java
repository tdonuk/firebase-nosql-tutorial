package com.tdonuk.passwordmanager.domain.dao;

import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class GenericAccountDAO extends AccountDAO<UserAccount> {
    @Override
    protected String getCollectionName() {
        return FirestoreCollections.ACCOUNTS;
    }

    @Override
    protected Class<UserAccount> getClassType() {
        return UserAccount.class;
    }
}
