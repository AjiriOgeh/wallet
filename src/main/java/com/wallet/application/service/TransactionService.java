package com.wallet.application.service;

import com.wallet.application.port.input.transactionServiceUseCases.*;
import com.wallet.application.port.output.TransactionOutputPort;
import com.wallet.domain.exception.TransactionNotFoundException;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TransactionService implements CreateTransactionUseCase, GetTransactionByIdUseCase,
        GetAllTransactionsUseCase, GetAllTransactionsByStatusUseCase, GetAllTransactionsByDateUseCase {

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

    @Override
    public List<Transaction> getAllTransactionsByDate(LocalDateTime localDateTime) {
        return transactionOutputPort.findTransactionsByByDate(localDateTime);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionOutputPort.findById(id)
                .orElseThrow(()-> new TransactionNotFoundException(String.format("Transaction with id %d", id)));
    }
}
