package com.wallet.infrastructure.adapters.input.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetUserResponse {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
