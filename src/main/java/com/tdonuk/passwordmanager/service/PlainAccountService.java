package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.dao.AccountDAO;
import com.tdonuk.passwordmanager.domain.dao.PlainAccountDAO;
import com.tdonuk.passwordmanager.domain.dto.BankAccountDTO;
import com.tdonuk.passwordmanager.domain.dto.PlainAccountDTO;
import com.tdonuk.passwordmanager.domain.entity.PlainAccount;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.PlainAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlainAccountService extends AccountService<PlainAccountDTO> {
    @Override
    protected PlainAccountDTO toDto(UserAccount entity) {
        PlainAccountDTO dto = new PlainAccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    protected List<PlainAccountDTO> toDto(List<UserAccount> entities) {
        List<PlainAccountDTO> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(toDto(e)));
        return dtos;
    }

    @Override
    protected PlainAccount toEntity(PlainAccountDTO dto) {
        PlainAccount entity = new PlainAccount();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    protected PlainAccountRepository getRepository() {
        return this.plainAccountRepository;
    }


}
