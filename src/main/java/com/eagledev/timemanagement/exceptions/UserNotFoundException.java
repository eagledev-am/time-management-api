package com.eagledev.todoapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotFoundException extends RuntimeException{
    private int statusCode;
    public static String USER_NOT_FOUND = "USER_NOT_FOUND";

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message , int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
