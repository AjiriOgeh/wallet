package com.wallet.application.service;

import com.wallet.application.service.WalletService;
import com.wallet.domain.exception.WalletNotFoundException;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.Wallet;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;


    @Test
    public void depositTest() {
        InitialisePaymentRequest initializePaymentRequest = InitialisePaymentRequest
                .builder()
                .amount(new BigDecimal(5000))
                .email("alexhunt@gmail.com")
                .password("password")
                .build();
        InitialisePaymentResponse response = walletService.deposit(initializePaymentRequest);

        assertThat(response).isNotNull();
        assertThat(response.getData().getAuthorizationUrl()).isNotNull();
        assertEquals("Authorization URL created", response.getMessage());
    }

    @Test
    public void getWalletByIdTest() {
        Wallet wallet = walletService.getWalletById(100L);

        assertThat(wallet).isNotNull();
        assertEquals(wallet.getWalletId(), 100L);
        assertEquals(wallet.getTransactions().size(), 1);
    }

    @Test
    public void checkBalanceTest() {
        Wallet wallet = walletService.checkBalance(100L);

        assertThat(wallet).isNotNull();
        assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(50000)));
    }

    @Test
    public void checkBalance_withNonExistentWallet_throwExceptionTest() {
        assertThrows(WalletNotFoundException.class, ()-> walletService.checkBalance(34567L));
    }

    @Test
    public void getAllWalletTransactionsTest() {
        List<Transaction> transactions = walletService.getAllWalletTransactions(100L);

        assertThat(transactions).isNotNull();
        assertEquals(1, transactions.size());
    }

    @Test
    public void getAll_nonExistentWalletTransactions_throwsExceptionTest() {
        assertThrows(WalletNotFoundException.class, ()-> walletService.getAllWalletTransactions(34567L));
    }
}