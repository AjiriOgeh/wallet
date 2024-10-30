package com.wallet.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllWalletTransactionsByDate {
    private Long id;
    public LocalDateTime date;
}
