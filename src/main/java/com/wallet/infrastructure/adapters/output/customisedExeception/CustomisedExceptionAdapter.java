package com.wallet.infrastructure.adapters.output.customisedExeception;

import com.wallet.domain.exception.*;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class CustomisedExceptionAdapter {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity<?> handleWalletNotFoundException(WalletNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(AuthUserCreationException.class)
    public final ResponseEntity<?> handleAuthUserCreationException(AuthUserCreationException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(),false));
    }

    @ExceptionHandler(ExternalApiException.class)
    public final ResponseEntity<?> handleExternalApiException(ExternalApiException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public final ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<?> handleTransactionNotFoundException(TransactionNotFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(UserExistsException.class)
    public final ResponseEntity<?> handleUserExistsException(UserExistsException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintViolation(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(errors, false));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(final Exception exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }
}
