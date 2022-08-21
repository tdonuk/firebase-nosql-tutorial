package com.tdonuk.passwordmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlainAccountDTO extends UserAccountDTO {
    private String password;
}
