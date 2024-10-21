package com.wallet.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String bankVerificationNumber;
    @OneToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity walletEntity;
}
