package com.wallet.application.service;

import com.wallet.application.port.input.transactionServiceUseCases.CreateTransactionUseCase;
import com.wallet.application.port.output.TransactionOutputPort;
import com.wallet.domain.model.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionService implements CreateTransactionUseCase {

    private final TransactionOutputPort transactionOutputPort;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionOutputPort.save(transaction);
    }

    // all transactions
    // all transactions by date
    // all transactiosn keyword
    // all tranasctions by status
    // add status for trarnsaciton
    // paysatack payment in outpout
}
