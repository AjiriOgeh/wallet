package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Transaction;

import java.util.List;

public interface GetAllWalletTransactionsUseCase {
    List<Transaction> getAllWalletTransactions(Long id);
}
