package com.wallet.infrastructure.adapters.input.rest.mapper;

import com.wallet.domain.model.User;
import com.wallet.domain.model.AuthToken;
import com.wallet.infrastructure.adapters.input.rest.dto.request.EditUserRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.AuthTokenResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.EditUserResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.GetUserResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.SignUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRestMapper {

    User mapSignUpRequestToUser(SignupRequest signupRequest);
    @Mapping(source = "wallet.walletId", target = "walletId")
    SignUpResponse mapUserToSignUpResponse(User user);
    User mapEditUserRequestToUser(EditUserRequest editUserRequest);
    EditUserResponse mapUserToEditUserResponse(User editedUser);
    GetUserResponse mapUserToGetUserResponse(User user);
    AuthTokenResponse mapAuthTokenToAuthTokenResponse(AuthToken authToken);
}
