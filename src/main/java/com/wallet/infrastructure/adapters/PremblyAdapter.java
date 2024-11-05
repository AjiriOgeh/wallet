package com.wallet.infrastructure.adapters;

import com.wallet.application.port.output.IdentityVerificationOutputPort;
import com.wallet.domain.exception.ExternalApiException;
import com.wallet.domain.exception.IdentityVerificationException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.wallet.domain.constant.ExternalApiEndpoints.PREMBLY_BANK_VERIFICATION_NUMBER_VERIFICATION_URL;
import static com.wallet.domain.constant.ExternalApiEndpoints.PREMBLY_PHONE_NUMBER_VERIFICATION_URL;

@RequiredArgsConstructor
public class PremblyAdapter implements IdentityVerificationOutputPort {

    private final WebClient webClient;

    @Value("${prembly.api.key}")
    private String premblySecretKey;

    @Override
    public IdentityVerificationResponse verifyPhoneNumber(String phoneNumber) {
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
                        Mono.error(new IdentityVerificationException(
                                String.format("Phone number %s is invalid", phoneNumber)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalApiException("Prembly is unable to validate phone number due to server error"))
                )
                .bodyToMono(IdentityVerificationResponse.class).block();
    }

    @Override
    public IdentityVerificationResponse verifyBankVerificationNumber(String bankVerificationNumber) {
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
                        Mono.error(new IdentityVerificationException(
                                String.format("Bank verification number %s is invalid", bankVerificationNumber)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalApiException("Prembly is unable to validate bank verification number due to server error"))
                )
                .bodyToMono(IdentityVerificationResponse.class).block();
    }
}
