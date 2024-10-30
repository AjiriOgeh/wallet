package com.wallet.infrastructure.adapters.input.rest.dto.response;

import lombok.*;

@Setter
@Getter
public class SignUpResponse {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Long walletId;
}
