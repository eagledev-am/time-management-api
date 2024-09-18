package com.eagledev.timemanagement.services.auth;

import com.eagledev.timemanagement.models.auth.AuthRequest;
import com.eagledev.timemanagement.models.auth.JwtResponse;
import com.eagledev.timemanagement.models.user.UserCreationRequest;

public interface AuthService {
    String registerUser(UserCreationRequest userDtoRequest);
    String verifyUser(String userNameOrEmail , String code);
    String sendVerificationCode(String userNameOrEmail);
    String resetPassword(String userNameOrEmail , String code , String newPassword);
    JwtResponse authenticate(AuthRequest request);
    boolean verifyCode(String code, String email);
}
