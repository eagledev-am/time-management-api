package com.eagledev.todoapi.services;

import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.UserCreationRequest;

public interface AuthService {
    JwtResponse registerUser(UserCreationRequest userDtoRequest);
    JwtResponse authenticate(AuthRequest request);
}
