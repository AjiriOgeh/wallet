package com.wallet.application.port.input.transactionServiceUseCases;

import com.wallet.domain.model.Transaction;

import java.util.List;

public interface GetAllTransactionsUseCase {

    List<Transaction> getAllTransactions();
}
