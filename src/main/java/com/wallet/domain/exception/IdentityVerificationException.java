package com.wallet.domain.exception;

public class IdentityVerificationException extends RuntimeException{
    public IdentityVerificationException(String message) {
        super(message);
    }
}
