package com.wallet.application.port.input.validationServiceUsesCases;

import com.wallet.infrastructure.adapters.input.rest.dto.response.validationResponse;

public interface ValidateBankVerificationNumberUseCase {

    validationResponse validateBankVerificationNumber(String bankVerificationNumber);
}
