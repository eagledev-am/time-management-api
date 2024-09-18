package com.eagledev.timemanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
