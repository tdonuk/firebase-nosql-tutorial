package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.ACCOUNTS;
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.USERS;

@Repository
@Slf4j
public class BankAccountDAO extends AccountDAO<BankAccount> implements BankAccountRepository {
    @Override
    public BankAccount findByIBAN(String iban) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(ACCOUNTS);

        log.info(String.format("findByIban started working for iban [%s]", iban));

        List<BankAccount> result = accounts.whereEqualTo("iban", iban).get().get().toObjects(getClassType());

        log.info(String.format("found %d accounts with given iban [%s]", result.size(), iban));

        if(result.isEmpty()) {
            log.error(String.format("can't find account with given iban [%s]", iban));
            return null;
        }

        if(result.size() > 1) {
            log.error(String.format("found duplicate accounts with given iban [%s], canceling the process..", iban));
            throw new Exception("given IBAN has duplicate values");
        }

        log.info(String.format("account [%s] successfully fetched, finishing the process..", result.get(0).getId()));

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
