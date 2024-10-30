package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.User;

public interface GetUserByIdUseCase {
    User getUserById(Long id);
}
