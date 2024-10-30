package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.wallet.domain.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class GetWalletResponse {
    private Long walletId;
    private BigDecimal balance;
    private Set<Transaction> transactions = new HashSet<>();
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
