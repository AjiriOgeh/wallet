package com.wallet.infrastructure.adapters;

import com.wallet.domain.exception.InvalidPaymentReferenceException;
import com.wallet.infrastructure.adapters.input.rest.dto.request.CreateTransferRecipientRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.CreateTransferRecipientResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitiateTransferResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaystackAdapterTest {

    @Autowired
    private PaystackAdapter paystackAdapter;

    @Test
    public void initialisePayment() {
        String email = "alexhunt@gmail.com";
        BigDecimal amount = new BigDecimal(5000);
        InitialisePaymentResponse response = paystackAdapter.initialisePayment(email, amount);

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertThat(response.getData().getReference()).isNotNull();
        assertEquals("Authorization URL created", response.getMessage());
    }

    @Test
    public void verifyPayment() {
        VerifyPaymentResponse response = paystackAdapter.verifyPayment("qjei56x15i");

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertEquals("Verification successful", response.getMessage());
        assertEquals("success", response.getData().getStatus());
    }

    @Test
    public void verifyNonExistentReference_ThrowsExceptionTest() {
        assertThrows(InvalidPaymentReferenceException.class, ()-> paystackAdapter.verifyPayment("non existent reference"));
    }

    @Test
    public void createTransferRecipient() {
        String accountNumber = "0147745036";
        String bankCode = "058";
        CreateTransferRecipientResponse response = paystackAdapter.createTransferRecipient(accountNumber, bankCode);

        assertThat(response).isNotNull();
        assertThat(response.getData().getRecipientCode()).isNotNull();
        assertEquals("Transfer recipient created successfully", response.getMessage());
    }

    @Test
    public void initiateTransfer() {
        String source = "balance";
        BigDecimal amount = new BigDecimal(50000);
        String recipient = "RCP_efbahncdllcd9cf";
        String reason  = "holiday flexing";

        InitiateTransferResponse response = paystackAdapter.initiateTransfer(source, amount, recipient, reason);
        assertThat(response).isNotNull();
        System.out.println(response);
    }
}