package com.wallet.infrastructure.adapters.output.persistence.repository;

import com.wallet.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
