package com.wallet.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Long walletId;
    private BigDecimal balance;
    private Set<Transaction> transactions = new HashSet<>();
}
