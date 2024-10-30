package com.wallet.domain.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
