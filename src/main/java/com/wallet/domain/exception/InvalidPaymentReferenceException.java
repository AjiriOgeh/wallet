package com.wallet.domain.exception;

public class InvalidPaymentReferenceException extends RuntimeException{
    public InvalidPaymentReferenceException(String message) {
        super(message);
    }
}
