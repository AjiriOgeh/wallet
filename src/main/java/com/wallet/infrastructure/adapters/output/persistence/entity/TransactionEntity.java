package com.wallet.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue
    private Long transactionId;
    @ManyToOne
    private WalletEntity walletEntity;
}
