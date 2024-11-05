package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.AuthToken;

public interface LoginAuthUserUseCase {
    AuthToken login(String email, String password);
}
