package com.tdonuk.passwordmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO extends UserAccountDTO {
    private String bankName;
    private String mobileAppPassword;
    private String iban;
    private String accountNumber;

    private Set<DebitCardDTO> cards;
}
