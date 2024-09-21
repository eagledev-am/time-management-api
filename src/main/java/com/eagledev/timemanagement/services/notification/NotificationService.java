package com.eagledev.timemanagement.services.notification;

import com.eagledev.timemanagement.entities.Notification;
import com.eagledev.timemanagement.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface NotificationService {
    Page<Notification> getNotifications(Pageable pageable);
    void sendNotificationsToUsers(Notification notification , Set<User> userList);
    void sendNotification(Notification notification , User user);
    void markAsRead(int id);

}
