package com.wallet.infrastructure.adapters;

import com.wallet.application.port.output.PaymentGatewayOutputPort;
import com.wallet.domain.exception.ExternalApiException;
import com.wallet.domain.exception.InvalidPaymentReferenceException;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.wallet.domain.constant.ExternalApiEndpoints.PAYSTACK_INITIALIZE_PAY_URL;
import static com.wallet.domain.constant.ExternalApiEndpoints.PAYSTACK_VERIFY_URL;

@RequiredArgsConstructor
public class PaystackAdapter implements PaymentGatewayOutputPort {

    @Value("${paystack.api.key}")
    private String paystackSecretKey;

    private final WebClient webClient;

    @Override
    public InitialisePaymentResponse initialisePayment(String email, BigDecimal amount) {
        InitialisePaymentResponse response = webClient.post()
                .uri(PAYSTACK_INITIALIZE_PAY_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"email\":\"" + email + "\",\n\"amount\":\"" + amount + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new InvalidUserCredentialsException("Invalid deposit details")) // change
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to initialise payment due to server error"))
                )
                .bodyToMono(InitialisePaymentResponse.class).block();
        return response;
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        VerifyPaymentResponse response = webClient.get()
                .uri(PAYSTACK_VERIFY_URL + reference)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new InvalidPaymentReferenceException("Invalid payment reference"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to verify payment due to server error"))
                )
                .bodyToMono(VerifyPaymentResponse.class).block();
        return response;
    }
}
