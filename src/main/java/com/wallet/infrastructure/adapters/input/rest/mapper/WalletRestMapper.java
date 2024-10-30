package com.wallet.infrastructure.adapters.input.rest.mapper;

import com.wallet.domain.model.Wallet;
import com.wallet.infrastructure.adapters.input.rest.dto.response.GetWalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletRestMapper {

    GetWalletResponse mapWalletToGetWalletResponse(Wallet wallet);
}
