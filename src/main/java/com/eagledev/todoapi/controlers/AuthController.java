package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.models.*;
import com.eagledev.todoapi.models.auth.AuthRequest;
import com.eagledev.todoapi.models.auth.JwtResponse;
import com.eagledev.todoapi.models.user.UserCreationRequest;
import com.eagledev.todoapi.models.auth.PasswordResetPassword;
import com.eagledev.todoapi.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/verify-user")
    ResponseEntity<?> verify(@RequestParam String code , @RequestParam String userNameOrEmail){
        Response<String> response = new Response<>("success" , service.verifyUser(userNameOrEmail , code) , null , null );
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PostMapping(path = { "/resend-code"  , "/forget-password"})
    ResponseEntity<Response<String>> sendVerificationCode(@RequestParam String userNameOrEmail){
        Response<String> response = new Response<>("success" , service.sendVerificationCode(userNameOrEmail) , null , null);
        return new ResponseEntity<>(response ,  HttpStatus.OK);
    }

    @PostMapping("/verify-code")
    ResponseEntity<Boolean> verifyCode(@RequestParam String code , @RequestParam String email){
        return new ResponseEntity<>(service.verifyCode(code , email) , HttpStatus.OK);
    }


    @PutMapping("/reset-password")
    ResponseEntity<Response<String>> resetPassword(@Valid @RequestBody PasswordResetPassword resetPassword){
        Response<String> response = new Response<>("success" , service.resetPassword(resetPassword.getEmailOrUserName(), resetPassword.getCode() , resetPassword.getNewPassword()) , null , null);
        return new ResponseEntity<>(response ,  HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(service.authenticate(authRequest) , HttpStatus.OK);
    }
}
