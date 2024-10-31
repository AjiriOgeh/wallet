package com.wallet.application.service;

import com.wallet.application.port.input.validationServiceUsesCases.ValidateBankVerificationNumberUseCase;
import com.wallet.application.port.input.validationServiceUsesCases.ValidatePhoneNumberUseCase;
import com.wallet.domain.exception.ExternalApiException;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.validationResponse;
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
    public validationResponse validatePhoneNumber(String phoneNumber) {
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
                    Mono.error(new InvalidUserCredentialsException(
                            String.format("Phone number %s is invalid", phoneNumber)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                    Mono.error(new ExternalApiException("Prembly is unable to validate phone number due to server error"))
                )
                .bodyToMono(validationResponse.class).block();
    }

    @Override
    public validationResponse validateBankVerificationNumber(String bankVerificationNumber) {
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
                        Mono.error(new InvalidUserCredentialsException(
                                String.format("Bank verification number %s is invalid", bankVerificationNumber)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalApiException("Prembly is unable to validate bank verification number due to server error"))
                )
                .bodyToMono(validationResponse.class).block();
    }
}

