package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.wallet.domain.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class GetWalletResponse {
    private Long walletId;
    private BigDecimal balance;
    private List<Transaction> transactions = new ArrayList<>();
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
