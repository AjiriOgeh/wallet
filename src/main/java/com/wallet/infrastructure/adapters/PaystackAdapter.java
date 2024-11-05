package com.wallet.infrastructure.adapters;

import com.wallet.application.port.output.PaymentGatewayOutputPort;
import com.wallet.domain.exception.*;
import com.wallet.infrastructure.adapters.input.rest.dto.response.CreateTransferRecipientResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitiateTransferResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static com.wallet.domain.constant.ExternalApiEndpoints.*;

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
                        Mono.error(new DepositRequestException("Invalid deposit details")) // change
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

    @Override
    public CreateTransferRecipientResponse createTransferRecipient(String accountNumber, String bankCode) {
        CreateTransferRecipientResponse response = webClient.post()
                .uri(PAYSTACK_CREATE_RECIPIENT_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"account_number\":\"" + accountNumber + "\",\n\"bank_code\":\"" + bankCode + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new CreateTransferRecipientException("Invalid transfer details"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to create transfer recipient due to server error"))
                )
                .bodyToMono(CreateTransferRecipientResponse.class).block();

        return  response;
    }

    @Override
    public InitiateTransferResponse initiateTransfer(String source, BigDecimal amount, String recipient, String reason) {
        InitiateTransferResponse response = webClient.post()
                .uri(PAYSTACK_INITIATE_TRANSFER_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"source\":\"" + source + "\",\n" +
                        "\"amount\":\"" + amount + "\",\n" +
                        "\"reference\":\"" + generateTransferReference() + "\",\n" +
                        "\"recipient\":\"" + recipient + "\",\n" +
                        "\"reason\":\"" + reason + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new InitiateTransferException("Invalid transfer details"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to create transfer recipient due to server error"))
                )
                .bodyToMono(InitiateTransferResponse.class).block();

        return response;
    }

    private String generateTransferReference() {
        return UUID.randomUUID().toString();
    }
}
