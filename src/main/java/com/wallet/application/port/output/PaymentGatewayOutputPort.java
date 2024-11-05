package com.wallet.application.port.output;

import com.wallet.infrastructure.adapters.input.rest.dto.request.CreateTransferRecipientRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitiateTransferRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.CreateTransferRecipientResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitiateTransferResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;

import java.math.BigDecimal;

public interface PaymentGatewayOutputPort {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);

    VerifyPaymentResponse verifyPayment(String reference);

    CreateTransferRecipientResponse createTransferRecipient(String accountNumber, String bankCode);

    InitiateTransferResponse initiateTransfer(String source, BigDecimal amount, String recipient, String reason);
}
