package com.eagledev.timemanagement.services.notification;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationAuthorization {
    private final NotificationRepo notificationRepo;

    public boolean canManageNotification(Authentication authentication , int notificationId) {
        User user = (User) authentication.getPrincipal();
        return notificationRepo.existsByIdAndUserId(notificationId , user.getId());
    }
}
