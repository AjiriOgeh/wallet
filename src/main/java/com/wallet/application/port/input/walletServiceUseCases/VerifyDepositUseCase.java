package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Transaction;

public interface VerifyDepositUseCase {
    Transaction verifyDeposit(String reference);
}
