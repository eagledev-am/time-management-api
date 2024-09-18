package com.eagledev.timemanagement.services.verification;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.VerificationCode;
import com.eagledev.timemanagement.repos.VerificationCodeRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
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

    @SneakyThrows
    @Override
    public Boolean validateVerificationCode(String code , String userNameOrEmail) {
        VerificationCode verificationCode = repo.findVerificationCodeByCode(code)
                .orElseThrow(() -> new BadRequestException("Invalid verification code !"));
        User userToVerify = verificationCode.getUser();
        if(!verificationCode.getExpiryDate().isAfter(Instant.now())){
            throw new BadRequestException("Code is expired");
        }
        if(!(userNameOrEmail.equals(userToVerify.getUsername()) || userNameOrEmail.equals(userToVerify.getEmail()))){
            throw new BadRequestException("UNAUTHORIZED");
        }
        return true;
    }

    @Override
    public void delete(String code) throws BadRequestException {
        VerificationCode verificationCode = repo.findVerificationCodeByCode(code)
                .orElseThrow(() -> new BadRequestException("Invalid verification code !"));
        repo.delete(verificationCode);
    }

    private String generateVerificationCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // generate a random 6-digit number
        return String.valueOf(code);
    }
}
