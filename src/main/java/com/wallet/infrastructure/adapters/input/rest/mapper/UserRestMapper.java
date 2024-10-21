package com.wallet.infrastructure.adapters.input.rest.mapper;

import com.wallet.domain.model.User;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.SignUpResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserRestMapper {

    User mapRequestToUser(SignupRequest signupRequest);
    SignUpResponse mapUserToResponse(User user);
}
