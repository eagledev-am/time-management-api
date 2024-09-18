package com.eagledev.timemanagement.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
public class ListCategoryException extends RuntimeException{
    private int statusCode;
    public static final String LIST_CATEGORY_NOT_FOUND = "LIST_CATEGORY_NOT_FOUND" ;

    public ListCategoryException(String message){
        super(message);
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ListCategoryException(String message , int statusCode){
        super(message);
        this.statusCode = statusCode;
    }


}
