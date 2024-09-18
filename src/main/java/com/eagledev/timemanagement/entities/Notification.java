package com.eagledev.timemanagement.entities;

import com.eagledev.timemanagement.entities.enums.NotificationStatus;
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

    private String title;

    private String message;

    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    NotificationStatus notificationStatus;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
