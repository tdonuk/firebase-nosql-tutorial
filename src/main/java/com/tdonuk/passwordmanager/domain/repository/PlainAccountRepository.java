package com.tdonuk.passwordmanager.domain.repository;

import com.tdonuk.passwordmanager.domain.entity.PlainAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface PlainAccountRepository extends AccountRepository<PlainAccount> {
}
