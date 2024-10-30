package com.wallet.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String bankVerificationNumber;
    private Wallet wallet;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
