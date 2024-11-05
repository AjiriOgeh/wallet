package com.wallet.application.service;

import com.wallet.application.port.input.validationServiceUsesCases.VerifyBankVerificationNumberUseCase;
import com.wallet.application.port.input.validationServiceUsesCases.VerifyPhoneNumberUseCase;
import com.wallet.application.port.output.IdentityVerificationOutputPort;
import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IdentityVerificationService implements VerifyPhoneNumberUseCase, VerifyBankVerificationNumberUseCase {

    private final IdentityVerificationOutputPort identityVerificationOutputPort;

    @Override
    public IdentityVerificationResponse verifyPhoneNumber(String phoneNumber) {
        return identityVerificationOutputPort.verifyPhoneNumber(phoneNumber);
    }

    @Override
    public IdentityVerificationResponse verifyBankVerificationNumber(String bankVerificationNumber) {
        return identityVerificationOutputPort.verifyBankVerificationNumber(bankVerificationNumber);

    }
}

