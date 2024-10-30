package com.wallet.application.port.output;

import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;

import java.util.List;

public interface TransactionOutputPort {
    Transaction save(Transaction transaction);

    List<Transaction> getAllTransactions();

    List<Transaction> findTransactionsByTransactionStatus(TransactionStatus transactionStatus);
}
