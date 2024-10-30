package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.User;

public interface GetUserByEmailUseCase {
    User getUserByEmail(String email);
}
