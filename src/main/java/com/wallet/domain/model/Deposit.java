package com.wallet.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {
    private Long walletId;
    private String reference;
    private BigDecimal amount;
}
