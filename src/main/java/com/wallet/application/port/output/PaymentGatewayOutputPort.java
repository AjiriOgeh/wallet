package com.wallet.application.port.output;

import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;

import java.math.BigDecimal;

public interface PaymentGatewayOutputPort {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);

    VerifyPaymentResponse verifyPayment(String reference);
}
