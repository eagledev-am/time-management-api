package com.eagledev.todoapi.exceptions.comment;

import com.eagledev.todoapi.exceptions.ErrorMessage;
import com.eagledev.todoapi.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CommentHandler {


    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> global(CommentNotFoundException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.INTERNAL_SERVER_ERROR.value()) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Response<?> getResponse(Exception exception , WebRequest request , int statusValue){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .statusCode(statusValue)
                .description(request.getDescription(false))
                .build();
        return new Response<>("error" , exception.getMessage() ,null,errorMessage);
    }
}
