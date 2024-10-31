package com.wallet.application.port.input.paystackUseCases;

import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;

import java.math.BigDecimal;

public interface InitialisePaymentUseCase {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);
}
