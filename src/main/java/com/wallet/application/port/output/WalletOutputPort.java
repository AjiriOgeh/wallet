package com.wallet.application.port.output;

import com.wallet.domain.model.Wallet;

import java.util.Optional;

public interface WalletOutputPort {
    Wallet save(Wallet wallet);

    Optional<Wallet> findById(Long id);
}
