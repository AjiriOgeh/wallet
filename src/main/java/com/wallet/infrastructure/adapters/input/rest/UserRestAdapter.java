package com.wallet.infrastructure.adapters.input.rest;

import com.wallet.application.port.input.userServiceUseCases.*;
import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;
import com.wallet.domain.model.AuthToken;
import com.wallet.infrastructure.adapters.input.rest.dto.request.EditUserRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.LoginRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupAdminRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.GetUserResponse;
import com.wallet.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequestMapping("/api/v1/users")
@Validated
@RestController
@RequiredArgsConstructor
public class UserRestAdapter {

    private final SignUpUseCase signUpUseCase;
    private final SignUpAdminUseCase signUpAdminUseCase;
    private final SendVerificationEmailUseCase sendVerificationEmailUseCase;
    private final UserLoginUseCase userLoginUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserRestMapper userRestMapper;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid final SignupRequest signupRequest) {
        User user =  userRestMapper.mapSignUpRequestToUser(signupRequest);
        User newUser = signUpUseCase.signUp(user);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponse(userRestMapper.mapUserToSignUpResponse(newUser), true));
    }

    @PostMapping("/auth/signup/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody @Valid final SignupAdminRequest signupAdminRequest) {
        AuthUser authUser = userRestMapper.mapSignupAdminRequestToAuthUser(signupAdminRequest);
        AuthUser newAuthUser = signUpAdminUseCase.signUpAdmin(authUser);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponse(userRestMapper.mapAuthUserToSignUpAdminResponse(newAuthUser), true));
    }

    @PostMapping("/auth/verification-email/{keycloakId}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable final String keycloakId) {
        sendVerificationEmailUseCase.sendVerificationEmail(keycloakId);
        return ResponseEntity.status(OK)
                .body(new ApiResponse("Verification email sent successfully", true));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid final LoginRequest loginRequest) {
        AuthToken authToken = userLoginUseCase.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.status(OK)
                .body(new ApiResponse(userRestMapper.mapAuthTokenToAuthTokenResponse(authToken), true));
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editUser(@RequestBody @Valid final EditUserRequest editUserRequest) {
        User user = userRestMapper.mapEditUserRequestToUser(editUserRequest);
        User editedUser = updateUserUseCase.updateUser(user);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(userRestMapper.mapUserToEditUserResponse(editedUser), true));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUser(@PathVariable final Long id) {
        User user = getUserByIdUseCase.getUserById(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(userRestMapper.mapUserToGetUserResponse(user), true));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = getAllUsersUseCase.getAllUsers();
        return ResponseEntity.status(OK)
                .body(new ApiResponse(users.stream()
                        .map(userRestMapper::mapUserToGetUserResponse).toList(), true));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse("User successfully deleted", true));
    }
}
