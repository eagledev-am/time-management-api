package com.eagledev.todoapi.services.auth;

import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.UserCreationRequest;

public interface AuthService {
    String registerUser(UserCreationRequest userDtoRequest);
    String verifyUser(String userNameOrEmail , String code);
    String resendCode(String userNameOrEmail);
    JwtResponse authenticate(AuthRequest request);
}
