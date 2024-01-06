package com.eagledev.todoapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserException extends RuntimeException{
    private int statusCode;
    public static String USER_NOT_FOUND = "USER_NOT_FOUND";

    public UserException(String message){
        super(message);
    }

    public UserException(String message , int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
