package com.wallet.application.port.output;

import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionOutputPort {
    Transaction save(Transaction transaction);

    List<Transaction> getAllTransactions();

    List<Transaction> findTransactionsByTransactionStatus(TransactionStatus transactionStatus);

    List<Transaction> findTransactionsByByDate(LocalDateTime localDateTime);

    Optional<Transaction> findById(Long id);
}
