package com.wallet.application.service;

import com.wallet.application.port.input.authServiceUseCases.CreateAuthUserUseCase;
import com.wallet.application.port.input.authServiceUseCases.DeleteAuthUserCase;
import com.wallet.application.port.input.authServiceUseCases.EditAuthUserUseCase;
import com.wallet.application.port.input.authServiceUseCases.LoginAuthUserUseCase;
import com.wallet.application.port.output.AuthOutputPort;
import com.wallet.domain.model.AuthToken;
import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthService implements CreateAuthUserUseCase,  LoginAuthUserUseCase,
        EditAuthUserUseCase, DeleteAuthUserCase{

    private final AuthOutputPort authOutputPort;

    @Override
    public Response createAuthUser(AuthUser authUser) {
        return authOutputPort.createAuthUser(authUser);
    }

    @Override
    public AuthToken login(String email, String password) {
        return authOutputPort.login(email, password);
    }

    @Override
    public void editAuthUser(String keycloakId, AuthUser authUser) {
        authOutputPort.editAuthUser(keycloakId, authUser);
    }

    @Override
    public void deleteAuthUser(String email) {
        authOutputPort.deleteAuthUser(email);
    }
}

