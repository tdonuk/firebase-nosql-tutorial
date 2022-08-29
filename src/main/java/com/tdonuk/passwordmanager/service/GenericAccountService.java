package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.dao.GenericAccountDAO;
import com.tdonuk.passwordmanager.domain.dto.UserAccountDTO;
import com.tdonuk.passwordmanager.domain.entity.UserAccount;
import com.tdonuk.passwordmanager.domain.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericAccountService extends AccountService<UserAccountDTO> {


    @Override
    protected UserAccountDTO toDto(UserAccount entity) {
        UserAccountDTO dto = new UserAccountDTO();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    protected List<UserAccountDTO> toDto(List<UserAccount> entities) {
        return null;
    }

    @Override
    protected UserAccount toEntity(UserAccountDTO dto) {
        UserAccount entity = new UserAccount();

        BeanUtils.copyProperties(dto, entity);

        return entity;
    }

    @Override
    protected AccountRepository getRepository() {
        return genericAccountDAO;
    }
}
