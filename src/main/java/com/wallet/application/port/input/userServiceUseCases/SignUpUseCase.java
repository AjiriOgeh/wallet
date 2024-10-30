package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.User;
import jakarta.transaction.Transactional;

public interface SignUpUseCase {

    User signUp(User user);
}
