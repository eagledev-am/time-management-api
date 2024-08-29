package com.eagledev.todoapi.services.verification;

import com.eagledev.todoapi.entities.User;
import org.apache.coyote.BadRequestException;

public interface VerificationCodeService {
    String generateVerificationCode(User user);
    Boolean validateVerificationCode(String verificationCode , String userNameOrEmail);
    void delete(String code) throws BadRequestException;
}
