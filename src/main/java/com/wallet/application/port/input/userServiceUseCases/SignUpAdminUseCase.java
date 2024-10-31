package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.AuthUser;

public interface SignUpAdminUseCase {
    AuthUser signUpAdmin(AuthUser authUser);
}
