package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.ACCOUNTS;

@Repository
public class BankAccountDAO extends AccountDAO<BankAccount> implements BankAccountRepository {
    @Override
    public BankAccount findByIBAN(String iban) throws Exception {
        CollectionReference accounts = firestore.collection(ACCOUNTS);

        List<BankAccount> result = accounts.document(SessionContext.loggedUsername()).collection(ACCOUNTS).whereEqualTo("iban", iban).get().get().toObjects(getClassType());

        if(result.isEmpty()) {
            return null;
        }

        if(result.size() > 1) {
            throw new Exception("given IBAN has duplicate values");
        }

        return result.get(0);
    }

    @Override
    protected String getCollectionName() {
        return FirestoreCollections.BANK_ACCOUNTS;
    }

    @Override
    protected Class<BankAccount> getClassType() {
        return BankAccount.class;
    }

}
