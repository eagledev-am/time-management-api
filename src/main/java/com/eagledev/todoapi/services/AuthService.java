package com.eagledev.todoapi.services;

import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.UserDtoRequest;

public interface AuthService {
    JwtResponse registerUser(UserDtoRequest userDtoRequest);
    JwtResponse authenticate(AuthRequest request);
}
