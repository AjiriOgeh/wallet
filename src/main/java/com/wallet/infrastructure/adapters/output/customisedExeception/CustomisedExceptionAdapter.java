package com.wallet.infrastructure.adapters.output.customisedExeception;

import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomisedExceptionAdapter extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(final Exception exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }
}
