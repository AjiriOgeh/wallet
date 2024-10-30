package com.wallet.application.port.input.transactionServiceUseCases;

import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;
import com.wallet.domain.model.TransactionType;

import java.util.List;

public interface GetAllTransactionsByStatus {

    List<Transaction> getAllTransactionsByStatus(TransactionStatus transactionStatus);
}
