package com.wallet.application.port.input.validationServiceUsesCases;

import com.wallet.domain.model.Validation;

public interface ValidateBankVerificationNumberUseCase {

    Validation validateBankVerificationNumber(String bankVerificationNumber);
}
