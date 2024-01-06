package com.eagledev.todoapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TaskException extends RuntimeException{
    private int statusCode;
    public static final String TASK_NOT_FOUND = "TASK_NOT_FOUND";

    public TaskException(String message){
        super(message);
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public TaskException(String message , int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

}
