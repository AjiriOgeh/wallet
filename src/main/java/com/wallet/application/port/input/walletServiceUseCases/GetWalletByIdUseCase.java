package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Wallet;

public interface GetWalletByIdUseCase {
    Wallet getWalletById(Long id);
}
