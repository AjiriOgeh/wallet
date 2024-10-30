package com.wallet.application.port.input.validationServiceUsesCases;


import com.wallet.domain.model.Validation;

public interface ValidatePhoneNumberUseCase {

    Validation validatePhoneNumber(String phoneNumber);
}
