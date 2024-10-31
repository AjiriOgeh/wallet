package com.wallet.application.port.input.transactionServiceUseCases;

import com.wallet.domain.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface GetAllTransactionsByDateUseCase {

    List<Transaction> getAllTransactionsByDate(LocalDateTime localDateTime);
}
