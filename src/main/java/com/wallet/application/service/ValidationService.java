package com.wallet.application.service;

import com.wallet.application.port.input.validationServiceUsesCases.ValidateBankVerificationNumberUseCase;
import com.wallet.application.port.input.validationServiceUsesCases.ValidatePhoneNumberUseCase;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.domain.model.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.wallet.domain.constant.ExternalApiEndpoints.PREMBLY_BANK_VERIFICATION_NUMBER_VERIFICATION_URL;
import static com.wallet.domain.constant.ExternalApiEndpoints.PREMBLY_PHONE_NUMBER_VERIFICATION_URL;

@RequiredArgsConstructor
public class ValidationService implements ValidatePhoneNumberUseCase, ValidateBankVerificationNumberUseCase {

    private final WebClient webClient;

    @Value("${prembly.api.key}")
    private String premblySecretKey;

    @Override
    public Validation validatePhoneNumber(String phoneNumber) {
        return webClient.post()
                .uri(PREMBLY_PHONE_NUMBER_VERIFICATION_URL)
                .header("Accept", "application/json")
                .header("content-type", "application/json")
                .header("app_id", "wallet-app")
                .header("x-api-key", premblySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"number\":\"" + phoneNumber + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                    Mono.error(new InvalidUserCredentialsException("Invalid phone number"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                    Mono.error(new RuntimeException("Prembly is unable to validate phone number due to server error"))
                )
                .bodyToMono(Validation.class).block();
    }

    @Override
    public Validation validateBankVerificationNumber(String bankVerificationNumber) {
        return webClient.post()
                .uri(PREMBLY_BANK_VERIFICATION_NUMBER_VERIFICATION_URL)
                .header("Accept", "application/json")
                .header("content-type", "application/json")
                .header("app_id", "wallet-app")
                .header("x-api-key", premblySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"number\":\"" + bankVerificationNumber + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidUserCredentialsException("Invalid bank verification number")) //// change
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Prembly is unable to validate bank verification number due to server error"))
                )
                .bodyToMono(Validation.class).block();
    }
}

