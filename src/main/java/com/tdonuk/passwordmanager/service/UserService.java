package com.tdonuk.passwordmanager.service;

import com.tdonuk.passwordmanager.domain.Name;
import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import com.tdonuk.passwordmanager.domain.repository.UserRepository;
import com.tdonuk.passwordmanager.util.SessionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER_USERNAME;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();

        BeanUtils.copyProperties(dto, entity);

        return entity;
    }

    private List<UserEntity> toEntity(List<UserDTO> dtos) {
        List<UserEntity> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(toEntity(dto)));
        return entities;
    }

    private UserDTO toDto(UserEntity entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<UserDTO> toDto(List<UserEntity> entities) {
        List<UserDTO> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public UserDTO save(UserDTO dto) throws Exception {
        return toDto(userRepository.save(toEntity(dto)));
    }

    public UserDTO update(Map<String, Object> newFields) throws Exception {
        return toDto(userRepository.update(newFields));
    }

    public List<UserDTO> saveAll(List<UserDTO> dtos) {
        return toDto(userRepository.saveAll(toEntity(dtos)));
    }

    public List<UserDTO> findByField(String field, Object value) throws ExecutionException, InterruptedException {
        return toDto(userRepository.findByField(field, value));
    }

    public List<UserDTO> findByName(Name name) throws ExecutionException, InterruptedException {
        return findByField("name", name);
    }

    public UserDTO findByEmail(String email) throws ExecutionException, InterruptedException {
        return findByField("email", email).get(0);
    }

    public UserDTO findById(String id) throws ExecutionException, InterruptedException {
        return toDto(userRepository.findById(id));
    }

    public UserDTO login(String username) throws Exception {
        SessionContext.setAttr(LOGGED_USER_USERNAME, username);
        return update(Map.of("lastLogin", new Date()));
    }
}
