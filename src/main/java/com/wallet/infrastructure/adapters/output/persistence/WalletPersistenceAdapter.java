package com.wallet.infrastructure.adapters.output.persistence;

import com.wallet.application.port.output.WalletOutputPort;
import com.wallet.domain.model.Wallet;
import com.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import com.wallet.infrastructure.adapters.output.persistence.mapper.WalletPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletOutputPort {

    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;
    @Override
    public Wallet save(final Wallet wallet) {
        WalletEntity walletEntity = walletPersistenceMapper.mapWalletToWalletEntity(wallet);
        WalletEntity savedWalletEntity = walletRepository.save(walletEntity);
        return walletPersistenceMapper.mapWalletEntityToWallet(savedWalletEntity);
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        Optional<WalletEntity> walletEntity = walletRepository.findById(id);
        if(walletEntity.isEmpty()) return Optional.empty();
        Wallet wallet = walletPersistenceMapper.mapWalletEntityToWallet(walletEntity.get());
        return Optional.of(wallet);
    }
}
