package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Transaction;

import java.util.Set;

public interface GetAllWalletTransactionsUseCase {
    Set<Transaction> getAllWalletTransactions(Long id);
}
