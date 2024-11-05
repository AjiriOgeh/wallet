package com.wallet.application.port.input.validationServiceUsesCases;


import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;

public interface VerifyPhoneNumberUseCase {

    IdentityVerificationResponse verifyPhoneNumber(String phoneNumber);
}
