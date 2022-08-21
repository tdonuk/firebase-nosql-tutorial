package com.tdonuk.passwordmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardDTO {
    private String pin;
    private Date expirationDate;
    private String cardNumber;
    private String cvv;
}
