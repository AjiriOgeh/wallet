package com.wallet.domain.exception;

public class DepositRequestException extends RuntimeException {
    public DepositRequestException(String message) {
        super(message);
    }
}
