package com.wallet.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitialisePaymentRequest {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be a positive number")
    @Digits(integer = 12, fraction = 6)
    private BigDecimal amount;
    private String password;
}
