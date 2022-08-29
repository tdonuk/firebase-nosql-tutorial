package com.tdonuk.passwordmanager.domain.dto;

import com.tdonuk.passwordmanager.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlainAccountDTO extends UserAccountDTO {
    protected AccountType accountType;
    private String password;
}
