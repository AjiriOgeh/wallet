package com.wallet.infrastructure.adapters;

import com.wallet.domain.exception.IdentityVerificationException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.IdentityVerificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PremblyAdapterTest {

    @Autowired
    private PremblyAdapter premblyAdapter;

    @Test
    public void verifyPhoneNumber() {
        IdentityVerificationResponse response = premblyAdapter.verifyPhoneNumber("09012345678");
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyInvalidPhoneNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> premblyAdapter.verifyPhoneNumber("08012345"));
    }

    @Test
    public void verifyBankVerificationNumber() {
        IdentityVerificationResponse response = premblyAdapter.verifyBankVerificationNumber("56789012345");
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyBankVerificationNumber_ThrowsExceptionTest() {
        assertThrows(IdentityVerificationException.class , ()-> premblyAdapter.verifyBankVerificationNumber("56789"));
    }
}