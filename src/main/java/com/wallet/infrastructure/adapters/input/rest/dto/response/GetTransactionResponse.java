package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.wallet.domain.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class GetTransactionResponse {
    private Long transactionId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime date;
}
