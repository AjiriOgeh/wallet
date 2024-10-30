package com.wallet.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wallet {
    private Long walletId;
    private BigDecimal balance = BigDecimal.ZERO;;
    private Set<Transaction> transactions = new HashSet<>();
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
