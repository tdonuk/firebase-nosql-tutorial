package com.tdonuk.passwordmanager.domain.dao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.tdonuk.passwordmanager.domain.Card;
import com.tdonuk.passwordmanager.domain.FirestoreCollections;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.tdonuk.passwordmanager.domain.FirestoreCollections.*;
import static com.tdonuk.passwordmanager.util.CryptUtils.decode;
import static com.tdonuk.passwordmanager.util.CryptUtils.encode;

@Repository
@Slf4j
public class BankAccountDAO extends AccountDAO<BankAccount> implements BankAccountRepository {

    @Override
    public BankAccount findByIBAN(String iban) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(BANK_ACCOUNTS);

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
    public void addCard(String id, Card card) throws Exception {
        CollectionReference accounts = firestore.collection(USERS).document(SessionContext.loggedUsername()).collection(BANK_ACCOUNTS);

        log.info(String.format("addCard started working for user [%s]", SessionContext.loggedUsername()));

        DocumentReference doc = accounts.document(id);

        log.info("searching for requested account..");

        DocumentSnapshot snapshot = doc.get().get();

        if(! snapshot.exists()) {
            log.error("addCard - account not found with given id: " + id);
            throw new Exception("Account not found with given id: " + id);
        }

        log.info("requested account has found. parsing to object..");

        BankAccount entity = snapshot.toObject(getClassType());

        decodeEntity(entity);

        log.info("adding card to account ["+id+"]");

        entity.getCards().add(card);

        log.info("card is added to card array of account. updating the new state..");

        encodeEntity(entity);

        WriteResult result = doc.update("cards", entity.getCards()).get();

        log.info("update successful. account ["+id+"] has updated at: " + result.getUpdateTime());
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
