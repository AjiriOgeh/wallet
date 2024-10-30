package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.AuthToken;

public interface UserLoginUseCase {
    AuthToken login(String email, String password);
}
