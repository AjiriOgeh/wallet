package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Transaction;

import java.util.List;
import java.util.Set;

public interface GetAllWalletTransactionsUseCase {
    List<Transaction> getAllWalletTransactions(Long id);
}
