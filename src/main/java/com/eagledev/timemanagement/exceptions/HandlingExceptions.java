package com.eagledev.todoapi.exceptions;

import com.eagledev.todoapi.models.Response;
import org.apache.coyote.BadRequestException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlingExceptions {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> userNameNotFoundHandler(UsernameNotFoundException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.NOT_FOUND.value()) , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<?> errorResponse(ErrorResponseException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.BAD_REQUEST.value()) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        err -> {
                            String fieldName = ((FieldError)err).getField();
                            String error = err.getDefaultMessage();
                            errors.put(fieldName, error);
                        }
                );
        AbstractMap.SimpleEntry<String , Map<String , String>> errorsEntry = new AbstractMap.SimpleEntry<>("errors" , errors);
        return new ResponseEntity<>(errorsEntry, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestHandler(BadRequestException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.BAD_REQUEST.value()) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<?> unAuthorizedExceptionHandler(UnAuthorizedException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationExceptionHandler(AuthenticationException exception , WebRequest request){
        return new ResponseEntity<>(getResponse(exception , request , HttpStatus.FORBIDDEN.value()) , HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> global(RuntimeException exception , WebRequest request){
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
