package com.eagledev.timemanagement.entities;

import com.eagledev.timemanagement.entities.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private LocalDateTime creationTime;

    private String resourceUrl;

    @Enumerated(EnumType.STRING)
    NotificationStatus notificationStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
