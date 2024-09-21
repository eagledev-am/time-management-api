package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.entities.Notification;
import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.services.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(
            tags = {"notifications"} ,
            summary = "GET All Of User" ,
            description = "This endpoint used to get notifications of authenticated User"
    )
    @GetMapping
    public Page<Notification> getAllNotifications(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "25") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize , Sort.by("creationTime").descending());
        return notificationService.getNotifications(pageable);
    }

    @Operation(
            tags = {"notifications"} ,
            summary = "Mark notification As Red " ,
            description = "This endpoint used to mark notification as read"
    )
    @PreAuthorize("@notificationAuthorization.canManageNotification(authentication , #id)")
    @PutMapping("/{id}/mark")
    public ResponseEntity<Response<String>> mark(@PathVariable int id) {
        notificationService.markAsRead(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
