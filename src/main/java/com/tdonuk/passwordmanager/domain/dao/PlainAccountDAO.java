package com.tdonuk.passwordmanager.domain.dao;

import com.tdonuk.passwordmanager.domain.entity.PlainAccount;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PlainAccountDAO extends AccountDAO<PlainAccount> implements PlainAccountRepository {

}
