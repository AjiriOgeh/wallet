package com.wallet.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class WalletEntity {
    @Id
    @GeneratedValue
    private Long walletId;
    private BigDecimal balance;
    @OneToMany
    private Set<TransactionEntity> transactions = new HashSet<>();
}
