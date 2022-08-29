package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.dao.AccountDAO;
import com.tdonuk.passwordmanager.domain.dao.BankAccountDAO;
import com.tdonuk.passwordmanager.domain.dto.BankAccountDTO;
import com.tdonuk.passwordmanager.domain.dto.PlainAccountDTO;
import com.tdonuk.passwordmanager.domain.entity.BankAccount;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.BankAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BankAccountService extends AccountService<BankAccountDTO>{
    @Override
    protected BankAccountDTO toDto(UserAccount entity) {
        BankAccountDTO dto = new BankAccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    protected BankAccount toEntity(BankAccountDTO dto) {
        BankAccount entity = new BankAccount();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    protected BankAccountRepository getRepository() {
        return this.bankAccountRepository;
    }

    @Override
    protected List<BankAccountDTO> toDto(List<UserAccount> entities) {
        List<BankAccountDTO> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(toDto(e)));
        return dtos;
    }

    public BankAccountDTO findByIban(String iban) throws Exception {
        return toDto(bankAccountRepository.findByIBAN(iban));
    }
}
