package com.wallet.domain.service;

import com.wallet.application.port.input.DeleteUserUseCase;
import com.wallet.application.port.input.EditUserUseCase;
import com.wallet.application.port.input.GetUserByIdUseCase;
import com.wallet.application.port.input.SignUpUseCase;
import com.wallet.application.port.output.UserOutputPort;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService implements SignUpUseCase, EditUserUseCase, GetUserByIdUseCase, DeleteUserUseCase {

    private final UserOutputPort userOutputPort;


    @Override
    public User signUp(User user) {
        return userOutputPort.save(user);
    }

    @Override
    public User editUser(User user) {
        return userOutputPort.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userOutputPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userOutputPort.delete(user);
    }


}
