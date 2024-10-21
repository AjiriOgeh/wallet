package com.wallet.application.port.input;

import com.wallet.domain.model.User;

public interface GetUserByIdUseCase {

    User getUserById(Long id);
}
