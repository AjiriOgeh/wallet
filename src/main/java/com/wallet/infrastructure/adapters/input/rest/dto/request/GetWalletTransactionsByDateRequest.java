package com.wallet.infrastructure.adapters.input.rest.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetWalletTransactionsByDateRequest {
    private Long id;
    public LocalDateTime date;
}
