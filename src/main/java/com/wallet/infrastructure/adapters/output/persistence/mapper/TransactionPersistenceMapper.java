package com.wallet.infrastructure.adapters.output.persistence.mapper;

import com.wallet.domain.model.Transaction;
import com.wallet.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionPersistenceMapper {

    TransactionEntity mapTransactionToTransactionEntity(Transaction transaction);

    Transaction mapTransactionEntityToTransaction(TransactionEntity transactionEntity);
}
