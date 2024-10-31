package com.wallet.infrastructure.adapters.input.rest.mapper;

import com.wallet.domain.model.Transaction;
import com.wallet.infrastructure.adapters.input.rest.dto.response.GetTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionRestMapper {

    GetTransactionResponse mapTransactionToTransactionResponse(Transaction transaction);
}
