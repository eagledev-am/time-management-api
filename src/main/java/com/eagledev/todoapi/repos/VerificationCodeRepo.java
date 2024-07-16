package com.eagledev.todoapi.repos;

import com.eagledev.todoapi.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Integer> {
    Optional<VerificationCode> findVerificationCodeByCode(String code);
}
