package com.wallet.application.port.input.paystackUseCases;

import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;

public interface VerifyPaymentUseCase {

    VerifyPaymentResponse verifyPayment(String reference);
}
