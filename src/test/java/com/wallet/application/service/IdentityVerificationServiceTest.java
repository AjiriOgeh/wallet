package com.wallet.application.service;

import com.wallet.domain.exception.IdentityVerificationException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class IdentityVerificationServiceTest {

    @Autowired
    private IdentityVerificationService identityVerificationService;

    @Test
    public void verifyPhoneNumberTest() {
        IdentityVerificationResponse response = identityVerificationService.verifyPhoneNumber("09012345678");
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyInvalidPhoneNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> identityVerificationService.verifyPhoneNumber("08012345"));
    }

    @Test
    public void verifyBankVerificationNumber() {
        IdentityVerificationResponse response = identityVerificationService.verifyBankVerificationNumber("56789012345");
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyBankVerificationNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> identityVerificationService.verifyBankVerificationNumber("56789"));
    }
}