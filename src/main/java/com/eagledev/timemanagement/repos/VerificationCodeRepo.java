package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Integer> {
    Optional<VerificationCode> findVerificationCodeByCode(String code);
}
