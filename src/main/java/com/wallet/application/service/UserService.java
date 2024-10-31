package com.wallet.application.service;

import com.wallet.application.port.input.userServiceUseCases.*;
import com.wallet.application.port.output.UserOutputPort;
import com.wallet.domain.exception.UserExistsException;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.model.AuthToken;
import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserService implements SignUpUseCase, SignUpAdminUseCase, UserLoginUseCase, UpdateUserUseCase,
        GetUserByIdUseCase, GetUserByEmailUseCase, GetAllUsersUseCase, DeleteUserUseCase {

    private final UserOutputPort userOutputPort;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final AuthService authService;
    private final ValidationService validationService;



    @Override
    public User signUp(User user) {
        validateUniqueFields(user);
        verifyUserIdentity(user);
        AuthUser authUser = mapUserToAuthUser(user);
        log.info("new user -> {}", user.getPassword());
        User newUser = userOutputPort.save(user);
        Response response = authService.createUserRepresentation(authUser);
        log.info("new user -> {}", newUser);
        String keyCloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        newUser.setWallet(walletService.createWallet());
        newUser.setKeycloakId(keyCloakId);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userOutputPort.save(newUser);
    }

    private AuthUser mapUserToAuthUser(User user) {
         return AuthUser.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public AuthUser signUpAdmin(AuthUser authUser) {
        Response response = authService.createUserRepresentation(authUser);
        String keyCloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        authUser.setKeycloakId(keyCloakId);
        return authUser;
    }

    private void verifyUserIdentity(User user) {
        validationService.validateBankVerificationNumber(user.getBankVerificationNumber());
        validationService.validatePhoneNumber(user.getPhoneNumber());
    }

    private void validateUniqueFields(User user) {
        validateEmail(user);
        validatePhoneNumber(user);
        validateBankVerificationNumber(user);
    }

    private void validateEmail(User user) {
        Optional<User> existingUser = userOutputPort.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistsException(
                    String.format("User with email %s exists", user.getEmail()));
        }
    }

    private void validatePhoneNumber(User user) {
        Optional<User> existingUser = userOutputPort.findByPhoneNumber(user.getPhoneNumber());
        if (existingUser.isPresent()) {
            throw new UserExistsException(
                    String.format("User with phone number %s exists", user.getPhoneNumber()));
        }
    }

    private void validateBankVerificationNumber(User user) {
        Optional<User> existingUser = userOutputPort.
                findByBankVerificationNumber(user.getBankVerificationNumber());
        if (existingUser.isPresent()) {
            throw new UserExistsException(
                    String.format("User with bank verification number %s exists",
                            user.getBankVerificationNumber()));
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
        if (!existingUser.getPhoneNumber().equals(user.getPhoneNumber())
                && user.getPhoneNumber() !=null) {
            validatePhoneNumber(user);
            validationService.validatePhoneNumber(user.getPhoneNumber());
        }
        if (!existingUser.getBankVerificationNumber().equals(user.getBankVerificationNumber())
                && user.getBankVerificationNumber() != null) {
            validateBankVerificationNumber(user);
            validationService.validateBankVerificationNumber(user.getBankVerificationNumber());
        }
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
                        String.format("User with email %s not found", email)));
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
