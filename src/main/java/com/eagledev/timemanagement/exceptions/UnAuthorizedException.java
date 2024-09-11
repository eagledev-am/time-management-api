package com.eagledev.timemanagement.exceptions;

public class UnAuthorizedException extends RuntimeException {
    int statusCode;
    public UnAuthorizedException(String unauthorizedAccess) {
        super(unauthorizedAccess);
        statusCode = 401;
    }
}
