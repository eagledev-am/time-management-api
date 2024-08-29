package com.eagledev.todoapi.exceptions.comment;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException{
     int statusCode;

    public CommentNotFoundException(String message){
        super(message);
        statusCode = 404;
    }
}
