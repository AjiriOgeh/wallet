package com.wallet.application.port.input.transactionServiceUseCases;


import com.wallet.domain.model.Transaction;

public interface CreateTransactionUseCase {
    Transaction createTransaction(Transaction transaction);
}
