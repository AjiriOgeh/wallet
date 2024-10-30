package com.wallet.application.service;

import com.wallet.application.port.input.transactionServiceUseCases.CreateTransactionUseCase;
import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsByDate;
import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsByStatus;
import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsUseCase;
import com.wallet.application.port.output.TransactionOutputPort;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionService implements CreateTransactionUseCase, GetAllTransactionsUseCase,
        GetAllTransactionsByStatus, GetAllTransactionsByDate {

    private final TransactionOutputPort transactionOutputPort;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionOutputPort.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionOutputPort.getAllTransactions();
    }

    @Override
    public List<Transaction> getAllTransactionsByStatus(TransactionStatus transactionStatus) {
        return transactionOutputPort.findTransactionsByTransactionStatus(transactionStatus);
    }
}
