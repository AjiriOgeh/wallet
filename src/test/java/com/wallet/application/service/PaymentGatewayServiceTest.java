package com.wallet.application.service;

import com.wallet.domain.exception.InvalidPaymentReferenceException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class PaymentGatewayServiceTest {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Test
    public void initializePaymentTest() {
        String email = "alexhunt@gmail.com";
        BigDecimal amount = new BigDecimal(5000);
        InitialisePaymentResponse response  = paymentGatewayService.initialisePayment(email, amount);

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertThat(response.getData().getReference()).isNotNull();
        assertEquals("Authorization URL created", response.getMessage());
    }

    @Test
    public void verifyPaymentTest(){
        VerifyPaymentResponse response = paymentGatewayService.verifyPayment("qjei56x15i");

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertEquals("Verification successful", response.getMessage());
        assertEquals("success", response.getData().getStatus());
    }

    @Test
    public void verifyNonExistentReference_ThrowsExceptionTest() {
        assertThrows(InvalidPaymentReferenceException.class, ()-> paymentGatewayService.verifyPayment("non existent reference"));
    }
}