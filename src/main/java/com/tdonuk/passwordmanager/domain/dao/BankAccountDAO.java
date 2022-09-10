package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.tdonuk.passwordmanager.domain.Card;
import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.ACCOUNTS;
import static com.tdonuk.passwordmanager.domain.FirestoreCollections.USERS;
import static com.tdonuk.passwordmanager.util.CryptUtils.decode;
import static com.tdonuk.passwordmanager.util.CryptUtils.encode;

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

    @SneakyThrows
    @Override
    protected void encodeEntity(BankAccount entity) {
        entity.setAccountNumber(encode(entity.getAccountNumber()));
        entity.setIban(encode(entity.getIban()));
        entity.setMobileAppPassword(encode(entity.getMobileAppPassword()));
        entity.setEmail(encode(entity.getEmail()));
        if(StringUtils.isNotBlank(entity.getPhoneNumber())) entity.setPhoneNumber(encode(entity.getPhoneNumber()));

        if(Objects.nonNull(entity.getCards())) {
            for(Card card : entity.getCards()) {
                card.setCardNumber(encode(card.getCardNumber()));
                card.setCvv(encode(card.getCvv()));
                card.setPin(encode(card.getPin()));
            }
        }
    }

    @SneakyThrows
    @Override
    protected void decodeEntity(BankAccount entity) {
        entity.setAccountNumber(decode(entity.getAccountNumber()));
        entity.setIban(decode(entity.getIban()));
        entity.setMobileAppPassword(decode(entity.getMobileAppPassword()));
        entity.setEmail(decode(entity.getEmail()));
        if(StringUtils.isNotBlank(entity.getPhoneNumber())) entity.setPhoneNumber(decode(entity.getPhoneNumber()));

        if(Objects.nonNull(entity.getCards())) {
            for(Card card : entity.getCards()) {
                card.setCardNumber(decode(card.getCardNumber()));
                card.setCvv(decode(card.getCvv()));
                card.setPin(decode(card.getPin()));
            }
        }
    }
}
