package com.wallet.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferRecipientRequest {
    @NotNull(message = "Account number cannot be null")
    private String accountNumber;
    @NotNull(message = "Bank code cannot be null")
    private String bankCode;
}
