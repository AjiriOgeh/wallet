package com.wallet.infrastructure.adapters.output.persistence.mapper;

import com.wallet.domain.model.User;
import com.wallet.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {

    User mapUserEntityToUser(UserEntity userEntity);

    UserEntity mapUserToUserEntity(User user);
}
