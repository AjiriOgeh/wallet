package com.wallet.application.port.input.walletServiceUseCases;

import java.math.BigDecimal;

public interface CheckBalanceUseCase {
    BigDecimal checkBalance(Long id);
}
