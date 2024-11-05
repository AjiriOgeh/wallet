package com.wallet.application.port.input.authServiceUseCases;

import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;

public interface EditAuthUserUseCase {
    void editAuthUser(String email, AuthUser authUser);
}
