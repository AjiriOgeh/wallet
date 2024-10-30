package com.wallet.application.port.input.transactionServiceUseCases;


import com.wallet.domain.model.Transaction;
import jakarta.transaction.Transactional;

public interface CreateTransactionUseCase {
    Transaction createTransaction(Transaction transaction);
}
