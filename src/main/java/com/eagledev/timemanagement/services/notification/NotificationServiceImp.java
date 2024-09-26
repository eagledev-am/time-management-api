package com.eagledev.timemanagement.services.notification;

import com.eagledev.timemanagement.entities.Notification;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.NotificationStatus;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.repos.NotificationRepo;
import com.eagledev.timemanagement.services.security.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserContextService userContextService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Page<Notification> getNotifications(Pageable pageable) {
        User user = userContextService.getCurrentUser();
        return notificationRepo.findAllByUserId(user.getId() , pageable);
    }

    @Override
    public void sendNotificationsToUsers(Notification notification, Set<User> userList) {
        User authenticatedUser = userContextService.getCurrentUser();
         for(User user : userList) {
             if(authenticatedUser.getUsername().equals(user.getUsername()))
                 continue;
             sendNotification(notification , user);
         }
    }

    @Override
    public void sendNotification(Notification notification, User user) {
        notification.setUser(user);
        notificationRepo.save(notification);
        send(notification , user.getUsername());
    }

    public void send(Notification notification , String username) {
        simpMessagingTemplate.convertAndSendToUser(
                username ,
                "/notification" ,
                notification
        );
    }

    @Override
    public void markAsRead(int id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Notification Not Found"));
        notification.setNotificationStatus(NotificationStatus.READ);
        notificationRepo.save(notification);
    }


}
