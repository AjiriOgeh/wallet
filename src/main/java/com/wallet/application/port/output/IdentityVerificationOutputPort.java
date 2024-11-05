package com.wallet.application.port.output;

import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;

public interface IdentityVerificationOutputPort {

    IdentityVerificationResponse verifyPhoneNumber(String phoneNumber);

    IdentityVerificationResponse verifyBankVerificationNumber(String bankVerificationNumber);
}
