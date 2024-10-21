package com.wallet.infrastructure.adapters.output.persistence.repository;

import com.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
