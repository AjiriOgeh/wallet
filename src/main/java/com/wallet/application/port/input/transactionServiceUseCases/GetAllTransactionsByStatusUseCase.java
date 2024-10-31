package com.wallet.application.port.input.transactionServiceUseCases;

import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;

import java.util.List;

public interface GetAllTransactionsByStatusUseCase {

    List<Transaction> getAllTransactionsByStatus(TransactionStatus transactionStatus);
}
