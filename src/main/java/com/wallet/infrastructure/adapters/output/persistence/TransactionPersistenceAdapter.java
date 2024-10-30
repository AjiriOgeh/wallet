package com.wallet.infrastructure.adapters.output.persistence;

import com.wallet.application.port.output.TransactionOutputPort;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;
import com.wallet.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import com.wallet.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.wallet.infrastructure.adapters.output.persistence.mapper.TransactionPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionOutputPort {

    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = transactionPersistenceMapper.mapTransactionToTransactionEntity(transaction);
        TransactionEntity savedTransactionEntity = transactionRepository.save(transactionEntity);
        return transactionPersistenceMapper.mapTransactionEntityToTransaction(savedTransactionEntity);

    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();
        return transactionEntities.stream().map(transactionPersistenceMapper::mapTransactionEntityToTransaction).toList();
    }

    @Override
    public List<Transaction> findTransactionsByTransactionStatus(TransactionStatus transactionStatus) {
        List<TransactionEntity> transactionEntities = transactionRepository.
                findTransactionEntitiesByTransactionStatus(transactionStatus);
        return transactionEntities.stream().map(transactionPersistenceMapper::mapTransactionEntityToTransaction).toList();
    }
}
