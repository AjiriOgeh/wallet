package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Wallet;

import java.math.BigDecimal;

public interface CheckBalanceUseCase {
    Wallet checkBalance(Long id);
}
