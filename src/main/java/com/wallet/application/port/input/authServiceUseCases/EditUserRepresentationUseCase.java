package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.User;
import jakarta.transaction.Transactional;

public interface EditUserRepresentationUseCase {
    void editUserRepresentation(String email, User user);
}
