package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

public interface CreateUserRepresentationUseCase {
    Response createUserRepresentation(AuthUser authUser);
}
