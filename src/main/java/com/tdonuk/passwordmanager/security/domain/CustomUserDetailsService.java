package com.tdonuk.passwordmanager.security.domain;

import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import com.tdonuk.passwordmanager.service.UserService;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Data
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserDTO user;

    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.user = userService.login(username);
        return new CustomUserDetails(user);
    }
}
