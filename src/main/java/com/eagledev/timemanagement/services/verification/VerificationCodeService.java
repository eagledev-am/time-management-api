package com.eagledev.timemanagement.services.verification;

import com.eagledev.timemanagement.entities.User;
import org.apache.coyote.BadRequestException;

public interface VerificationCodeService {
    String generateVerificationCode(User user);
    Boolean validateVerificationCode(String verificationCode , String userNameOrEmail);
    void delete(String code) throws BadRequestException;
}
