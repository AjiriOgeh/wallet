package com.wallet.application.service;

import com.wallet.application.port.input.paystackUseCases.InitialisePaymentUseCase;
import com.wallet.application.port.input.paystackUseCases.VerifyPaymentUseCase;
import com.wallet.application.port.output.PaymentGatewayOutputPort;
import com.wallet.domain.exception.ExternalApiException;
import com.wallet.domain.exception.InvalidPaymentReferenceException;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.wallet.domain.constant.ExternalApiEndpoints.PAYSTACK_INITIALIZE_PAY_URL;
import static com.wallet.domain.constant.ExternalApiEndpoints.PAYSTACK_VERIFY_URL;

@Slf4j
@RequiredArgsConstructor
public class PaymentGatewayService implements InitialisePaymentUseCase, VerifyPaymentUseCase {

    private final PaymentGatewayOutputPort paymentGatewayOutputPort;

    @Override
    public InitialisePaymentResponse initialisePayment(String email, BigDecimal amount) {
        return paymentGatewayOutputPort.initialisePayment(email, amount);
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        return paymentGatewayOutputPort.verifyPayment(reference);
    }
}
