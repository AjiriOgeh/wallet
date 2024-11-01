package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.AuthUser;
import jakarta.ws.rs.core.Response;

public interface CreateUserRepresentationUseCase {
    Response createUserRepresentation(AuthUser authUser);
}
