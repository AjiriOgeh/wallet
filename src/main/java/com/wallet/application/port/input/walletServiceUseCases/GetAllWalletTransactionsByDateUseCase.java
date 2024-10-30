package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.GetAllWalletTransactionsByDate;
import com.wallet.domain.model.Transaction;

import java.util.List;

public interface GetAllWalletTransactionsByDateUseCase {
    List<Transaction> getAllWalletTransactionsByDate(GetAllWalletTransactionsByDate getAllWalletTransactionsByDate);

}
