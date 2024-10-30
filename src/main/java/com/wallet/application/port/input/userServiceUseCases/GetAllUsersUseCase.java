package com.wallet.application.port.input.userServiceUseCases;

import com.wallet.domain.model.User;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
