package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.entities.Notification;
import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.services.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public Page<Notification> getAllNotifications(@RequestParam int pageNo,
            @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize , Sort.by("creationTime"));
        return notificationService.getNotifications(pageable);
    }

    @GetMapping("/unread")
    public Page<Notification> getUnreadNotifications(@RequestParam int pageNo,
                                                  @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize , Sort.by("creationTime").descending());
        return notificationService.getUnreadNotifications(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Notification>> getNotificationById(@PathVariable int id) {
        Response<Notification> response = new Response<>("success" , "Notification retrieved successfully" , notificationService.getNotification(id) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping("/{id}/mark")
    public ResponseEntity<Response<String>> readNotificationById(@PathVariable int id) {
        notificationService.markAsRead(id);
        Response<Notification> response = new Response<>("success" , "Notification status updated successfully" , null, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
