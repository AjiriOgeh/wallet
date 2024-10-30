package com.wallet.application.service;

import com.wallet.application.port.input.userServiceUseCases.*;
import com.wallet.application.port.output.UserOutputPort;
import com.wallet.domain.exception.UserExistsException;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.model.AuthToken;
import com.wallet.domain.model.User;
import com.wallet.domain.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements SignUpUseCase, UserLoginUseCase, UpdateUserUseCase,
        GetUserByIdUseCase, GetUserByEmailUseCase, GetAllUsersUseCase, DeleteUserUseCase {

    private final UserOutputPort userOutputPort;
    private final WalletService walletService;
    private final AuthService authService;
    private final ValidationService validationService;

    @Override
    public User signUp(User user) {
        validateEmail(user);
        validationService.validateBankVerificationNumber(user.getBankVerificationNumber());
        validationService.validatePhoneNumber(user.getPhoneNumber());
        Wallet wallet = walletService.createWallet();
        user.setWallet(wallet);
        authService.createUserRepresentation(user);
        return userOutputPort.save(user);
    }

    private void validateEmail(User user) {
        Optional<User> existingUser = userOutputPort.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistsException(
                    String.format("User with email %s exists", user.getEmail()));
        }
    }

    @Override
    public AuthToken login(String email, String password) {
        return authService.login(email, password);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = getUserById(user.getUserId());
        if (!existingUser.getEmail().equals(user.getEmail())) validateEmail(user);
        updateUserFields(user, existingUser);
        authService.editUserRepresentation(existingUser.getEmail(), user);
        return userOutputPort.save(existingUser);
    }

    private static void updateUserFields(User user, User existingUser) {
        if (user.getFirstname() != null) existingUser.setFirstname(user.getFirstname());
        if (user.getLastname() != null) existingUser.setLastname(user.getLastname());
        if (user.getPhoneNumber() != null) existingUser.setPhoneNumber(user.getPhoneNumber());
        if (user.getBankVerificationNumber() != null) existingUser.setBankVerificationNumber(user.getBankVerificationNumber());
    }

    @Override
    public User getUserById(Long id) {
        return userOutputPort.findById(id)
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("User with id %d not found", id)));
    }

    public User getUserByEmail(String email) {
        return userOutputPort.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format("User with id %s not found", email)));
    }

    @Override
    public List<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        authService.deleteUserRepresentation(user.getEmail());
        userOutputPort.delete(user);
    }

    // forgot password;
    // send verification email
}
