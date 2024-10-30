package com.wallet.infrastructure.adapters.output.customisedExeception;

import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.exception.WalletNotFoundException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintViolation(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
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
