package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.UserDtoRequest;
import com.eagledev.todoapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService service;

    @GetMapping
    String getA(){
        return "ok";
    }

    @PostMapping("/register")
    ResponseEntity<JwtResponse> register( @Valid @RequestBody UserDtoRequest userDtoRequest){
        return new ResponseEntity<>(service.registerUser(userDtoRequest) , HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(service.authenticate(authRequest) , HttpStatus.OK);
    }
}
