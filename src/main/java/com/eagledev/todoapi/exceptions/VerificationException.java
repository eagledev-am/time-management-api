package com.eagledev.todoapi.exceptions;

import lombok.Getter;

@Getter
public class VerificationException extends RuntimeException{
    int statusCode;

    public VerificationException(String message) {
        super(message);
        this.statusCode = 403;
    }

    public VerificationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
