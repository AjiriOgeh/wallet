//package com.wallet.domain.service;
//
//import com.wallet.application.service.WalletService;
//import com.wallet.domain.exception.WalletNotFoundException;
//import com.wallet.domain.model.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Set;
//
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Sql(scripts = {"/database/data.sql"})
//public class WalletServiceTest {
//
//    @Autowired
//    private WalletService walletService;
//
//
//    @Test
//    public void depositTest() {
//        InitializePaymentRequest initializePaymentRequest = InitializePaymentRequest
//                .builder()
//                .amount(new BigDecimal(5000))
//                .email("bugsbunny@gmail.com")
//                .build();
//
//        InitializePaymentResponse response =  paystackOldService.initializePayment(initializePaymentRequest);
//        System.out.println(response.getData().getAuthorizationUrl());
//        String reference = response.getData().getReference();
//        Deposit deposit = Deposit.builder()
//                .walletId(100L)
//                .reference(reference)
//                .amount(new BigDecimal(5000))
//                .build();
//
//        Transaction transaction = walletService.deposit(deposit);
//        Wallet wallet = walletService.getWalletById(100L);
//
//        assertThat(transaction).isNotNull();
//        assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(10_000)));
//        assertEquals(4, wallet.getTransactions().size());
////        System.out.println(wallet.getTransactions());
//    }
//
//    @Test
//    public void getWalletByIdTest() {
//        Wallet wallet = walletService.getWalletById(100L);
//
//        assertThat(wallet).isNotNull();
//        assertEquals(wallet.getWalletId(), 100L);
//        assertEquals(wallet.getTransactions().size(), 3);
//    }
//
//    @Test
//    public void checkBalanceTest() {
//        BigDecimal balance = walletService.checkBalance(100L);
//        Wallet wallet = walletService.getWalletById(100L);
//
//        assertThat(balance).isNotNull();
//        assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(5000)));
//    }
//
//    @Test
//    public void checkBalance_withNonExistentWallet_throwExceptionTest() {
//        assertThrows(WalletNotFoundException.class, ()-> walletService.checkBalance(101L));
//    }
//
//    @Test
//    public void getAllWalletTransactionsTest() {
//        Set<Transaction> transactions = walletService.getAllWalletTransactions(100L);
//
//        assertThat(transactions).isNotNull();
//        assertEquals(3, transactions.size());
//        //assertEquals(transactions.);
//    }
//
//    @Test
//    public void getAll_nonExistentWalletTransactions_throwsExceptionTest() {
//        assertThrows(WalletNotFoundException.class, ()-> walletService.getAllWalletTransactions(101L));
//    }
//
//    @Test
//    public void getAllWalletTransactionsByDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.n");
//        GetAllWalletTransactionsByDate getAllWalletTransactionsByDate = GetAllWalletTransactionsByDate.builder()
//                .date(LocalDateTime.parse("2024-10-20T10:32:08.643689600", formatter)
//                        .truncatedTo(ChronoUnit.MICROS))
//                .id(100L)
//                .build();
//        List<Transaction> transactions = walletService.getAllWalletTransactionsByDate(getAllWalletTransactionsByDate);
//        //System.out.println(LocalDateTime.parse("2024-10-20T10:32:08.643689600", formatter));
//        assertThat(transactions).isNotNull();
//        //System.out.println(walletService.getWalletById(100L).getTransactions());
//        //assertEquals(2, transactions.size());
//        //System.out.println(transactions);
//    }
//}