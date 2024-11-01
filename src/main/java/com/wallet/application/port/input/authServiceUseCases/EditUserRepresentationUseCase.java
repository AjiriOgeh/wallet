package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.User;

public interface EditUserRepresentationUseCase {
    void editUserRepresentation(String email, User user);
}
