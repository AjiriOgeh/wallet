package com.wallet.domain.exception;

public class InitiateTransferException extends RuntimeException{
    public InitiateTransferException(String message) {
        super(message);
    }
}
