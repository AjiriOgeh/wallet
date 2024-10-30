package com.wallet.infrastructure.adapters.output.persistence.mapper;

import com.wallet.domain.model.User;
import com.wallet.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    @Mapping(target = "wallet", source = "walletEntity")
    User mapUserEntityToUser(UserEntity userEntity);

    @Mapping(target = "walletEntity", source = "wallet")
    UserEntity mapUserToUserEntity(User user);
}
