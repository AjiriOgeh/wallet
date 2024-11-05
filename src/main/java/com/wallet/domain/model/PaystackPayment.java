package com.wallet.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaystackPayment {
    public Long paystackPaymentId;
    private User user;
    private String amount;
    private String reference;
    private String paidAt;
    private String createdAt;
    private String currency;
}
