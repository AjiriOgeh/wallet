package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.AuthToken;

public interface LoginUserRepresentationUseCase {
    AuthToken login(String email, String password);
}
