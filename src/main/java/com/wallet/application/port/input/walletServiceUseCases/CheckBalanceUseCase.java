package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Wallet;

public interface CheckBalanceUseCase {
    Wallet checkBalance(Long id);
}
