package com.wallet.infrastructure.adapters.input.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class GetBalanceResponse {
    private Long walletId;
    private BigDecimal balance;
}
