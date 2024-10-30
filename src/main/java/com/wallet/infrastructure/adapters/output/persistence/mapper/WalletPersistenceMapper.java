package com.wallet.infrastructure.adapters.output.persistence.mapper;

import com.wallet.domain.model.Wallet;
import com.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletPersistenceMapper {

    Wallet mapWalletEntityToWallet(WalletEntity walletEntity);

    WalletEntity mapWalletToWalletEntity(Wallet wallet);

}
