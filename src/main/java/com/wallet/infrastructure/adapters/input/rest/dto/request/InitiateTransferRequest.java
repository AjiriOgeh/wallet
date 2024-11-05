package com.wallet.infrastructure.adapters.input.rest.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransferRequest {
    private String source;
    private BigDecimal amount;
    private String reference;
    private String recipient;
    private String reason;
}
