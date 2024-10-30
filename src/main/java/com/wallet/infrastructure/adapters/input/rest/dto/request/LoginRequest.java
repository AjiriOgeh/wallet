package com.wallet.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;
    private String password;
}
