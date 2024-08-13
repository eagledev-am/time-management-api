package com.eagledev.todoapi.services.verification;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.entities.VerificationCode;
import com.eagledev.todoapi.exceptions.VerificationException;
import com.eagledev.todoapi.repos.VerificationCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImp implements VerificationCodeService {
    private final VerificationCodeRepo repo;

    @Override
    public String generateVerificationCode(User user) {
        String verificationCode = generateVerificationCode();
        VerificationCode code = VerificationCode.builder()
                        .code(verificationCode)
                        .user(user)
                        .expiryDate(Instant.now().plusSeconds(15 * 60))
                        .build();
        repo.save(code);
        return verificationCode;
    }

    @Override
    public Boolean validateVerificationCode(String code , String userName) {
        VerificationCode verificationCode = repo.findVerificationCodeByCode(code)
                .orElseThrow(() -> new VerificationException("Invalid verification code !"));
        User userToVerify = verificationCode.getUser();
        return userName.equals(userToVerify.getUsername())
                && verificationCode.getExpiryDate().isAfter(Instant.now());
    }

    @Override
    public void delete(String code) {
        VerificationCode verificationCode = repo.findVerificationCodeByCode(code)
                .orElseThrow(() -> new VerificationException("Invalid verification code !"));
        repo.delete(verificationCode);
    }

    private String generateVerificationCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // generate a random 6-digit number
        return String.valueOf(code);
    }
}
