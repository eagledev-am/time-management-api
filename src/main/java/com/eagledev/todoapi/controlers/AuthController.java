package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.Response;
import com.eagledev.todoapi.models.UserCreationRequest;
import com.eagledev.todoapi.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @GetMapping
    String getA(){
        return "ok";
    }

    @PostMapping("/register")
    ResponseEntity<Response<?>> register(@Valid @RequestBody UserCreationRequest userDtoRequest , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(new Response<>(200 , service.registerUser(userDtoRequest) , null ) , HttpStatus.OK);
    }

    @PostMapping("/verify")
    ResponseEntity<Response<?>> verify(@RequestParam String code , @RequestParam String userNameOrEmail){

        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), service.verifyUser(userNameOrEmail , code) , null) , HttpStatus.OK);
    }

    @PostMapping("/resend")
    ResponseEntity<Response<?>> resend(@RequestParam String userNameOrEmail){
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), service.resendCode(userNameOrEmail) , null) , HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest authRequest , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(service.authenticate(authRequest) , HttpStatus.OK);
    }
}
