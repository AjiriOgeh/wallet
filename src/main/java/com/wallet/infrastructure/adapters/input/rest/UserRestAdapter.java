package com.wallet.infrastructure.adapters.input.rest;

import com.wallet.application.port.input.DeleteUserUseCase;
import com.wallet.application.port.input.EditUserUseCase;
import com.wallet.application.port.input.SignUpUseCase;
import com.wallet.domain.model.User;
import com.wallet.infrastructure.adapters.input.rest.dto.request.DeleteUserRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.EditUserRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import com.wallet.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserRestAdapter {

    private final SignUpUseCase signUpUseCase;
    private final EditUserUseCase editUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserRestMapper userRestMapper;

    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody final SignupRequest signupRequest) {
        User user =  userRestMapper.mapRequestToUser(signupRequest);
        User newUser = signUpUseCase.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(userRestMapper.mapUserToResponse(newUser), true));
    }

    @PatchMapping("/users")
    public ResponseEntity<?> editUser(@RequestBody final EditUserRequest editUserRequest) {
        return ResponseEntity.status(OK)
                .body(null);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUser(@RequestBody final DeleteUserRequest deleteUserRequest) {
        return ResponseEntity.status(OK)
                .body(null);
    }
}
