package com.wallet.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentRequestchanged {

    private BigDecimal amount = BigDecimal.ZERO;
    private String email;
}
