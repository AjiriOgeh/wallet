package com.wallet.application.port.input.validationServiceUsesCases;


import com.wallet.infrastructure.adapters.input.rest.dto.response.ValidationResponse;

public interface ValidatePhoneNumberUseCase {

    ValidationResponse validatePhoneNumber(String phoneNumber);
}
