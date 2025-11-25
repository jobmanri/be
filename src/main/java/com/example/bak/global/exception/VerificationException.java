package com.example.bak.global.exception;

import lombok.Getter;

@Getter
public class VerificationException extends RuntimeException {
    private final ErrorCode errorCode;

    public VerificationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
