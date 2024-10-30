package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.infrastructure.adapters.input.rest.dto.request.GetWalletTransactionsByDateRequest;
import com.wallet.domain.model.Transaction;

import java.util.List;

public interface GetAllWalletTransactionsByDateUseCase {
    List<Transaction> getAllWalletTransactionsByDate(GetWalletTransactionsByDateRequest getWalletTransactionsByDateRequest);

}
