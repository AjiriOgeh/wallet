package com.wallet.infrastructure.adapters.output.persistence.repository;

import com.wallet.domain.model.TransactionStatus;
import com.wallet.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.transactionStatus=:transactionStatus")
    List<TransactionEntity> findTransactionEntitiesByTransactionStatus(TransactionStatus transactionStatus);
}
