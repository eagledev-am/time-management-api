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

@RequestMapping(path = "/api/auth" , produces = "application/json")
@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @GetMapping
    String getA(){
        return "ok";
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Valid @RequestBody UserCreationRequest userDtoRequest){
        Response<String> response = new Response<>("success" , service.registerUser(userDtoRequest) , null , null );
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    ResponseEntity<?> verify(@RequestParam String code , @RequestParam String userNameOrEmail){
        Response<String> response = new Response<>("success" , service.verifyUser(userNameOrEmail , code) , null , null );
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PostMapping("/resend")
    ResponseEntity<Response<String>> resend(@RequestParam String userNameOrEmail){
        Response<String> response = new Response<>("success" , service.resendCode(userNameOrEmail) , null , null);
        return new ResponseEntity<>(response ,  HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(service.authenticate(authRequest) , HttpStatus.OK);
    }
}
