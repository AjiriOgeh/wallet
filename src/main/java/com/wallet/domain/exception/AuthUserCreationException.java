package com.wallet.domain.exception;

public class AuthUserCreationException extends RuntimeException{
    public AuthUserCreationException(String message) {
        super(message);
    }
}
