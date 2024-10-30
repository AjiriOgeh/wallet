package com.wallet.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    private Long transactionId;
    private BigDecimal amount = BigDecimal.ZERO;
    private TransactionType transactionType;
    @Setter(AccessLevel.NONE)
    private LocalDateTime date;
}
