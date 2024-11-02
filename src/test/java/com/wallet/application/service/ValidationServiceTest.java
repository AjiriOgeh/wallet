package com.wallet.application.service;

import com.wallet.domain.exception.IdentityVerificationException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ValidationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    @Test
    public void validatePhoneNumberTest() {
        ValidationResponse response = validationService.validatePhoneNumber("09012345678");
        assertThat(response).isNotNull();
    }

    @Test
    public void validateInvalidPhoneNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> validationService.validatePhoneNumber("08012345"));
    }

    @Test
    void validateBankVerificationNumber() {
        ValidationResponse response = validationService.validateBankVerificationNumber("56789012345");
        assertThat(response).isNotNull();
    }

    @Test
    public void validateBankVerificationNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> validationService.validateBankVerificationNumber("56789"));
    }
}