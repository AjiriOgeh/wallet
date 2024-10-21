package com.wallet.application.port.input;

import com.wallet.domain.model.User;

public interface DeleteUserUseCase {

    void deleteUser(Long id);
}
