package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.User;

public interface SignUpUseCase {

    User signUp(User user);
}
