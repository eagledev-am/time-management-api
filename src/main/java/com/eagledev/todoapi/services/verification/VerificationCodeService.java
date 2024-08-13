package com.eagledev.todoapi.services.verification;

import com.eagledev.todoapi.entities.User;

public interface VerificationCodeService {
    String generateVerificationCode(User user);
    Boolean validateVerificationCode(String verificationCode , String userName);
    void delete(String code);
}
