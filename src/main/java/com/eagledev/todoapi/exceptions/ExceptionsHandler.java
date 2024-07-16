package com.eagledev.todoapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> userExc(UserException e , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .timestamp(new Date())
                .statusCode(e.getStatusCode())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage , HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(ListCategoryException.class)
    public ResponseEntity<ErrorMessage> listCategoryEx(ListCategoryException e , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .timestamp(new Date())
                .statusCode(e.getStatusCode())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage , HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserNameNotFoundEx(UsernameNotFoundException e , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .timestamp(new Date())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<ErrorMessage> taskException(TaskException e , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .timestamp(new Date())
                .statusCode(e.getStatusCode())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage , HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<ErrorMessage> verificationEx(VerificationException exception , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .statusCode(exception.getStatusCode())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage , HttpStatusCode.valueOf(exception.getStatusCode()));
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationExc(AuthenticationException exception , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage , HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorMessage> errorResponse(ErrorResponseException exception , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> global(RuntimeException exception , WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
