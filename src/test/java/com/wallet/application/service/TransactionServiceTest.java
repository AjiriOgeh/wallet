package com.wallet.application.service;

import com.wallet.domain.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.wallet.domain.model.TransactionStatus.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void getAllTransactionsTest() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertThat(transactions).isNotNull();
        assertEquals(4, transactions.size());
    }

    @Test
    public void getAllTransactionsByStatusTest() {
        List<Transaction> transactions = transactionService.getAllTransactionsByStatus(SUCCESS);
        assertThat(transactions).isNotNull();
        assertEquals(3, transactions.size());
    }

    @Test
    public void getAllTransactionsByDateTest() {
        List<Transaction> transactions = transactionService.getAllTransactionsByDate(LocalDateTime.now());
        assertThat(transactions).isNotNull();
        assertEquals(0, transactions.size());
    }

    @Test
    public void getTransactionByIdTest() {
        Transaction transaction = transactionService.getTransactionById(100L);
        assertThat(transaction).isNotNull();
        assertEquals(SUCCESS, transaction.getTransactionStatus());
        assertEquals(0, transaction.getAmount().compareTo(new BigDecimal(50_000)));
    }
}