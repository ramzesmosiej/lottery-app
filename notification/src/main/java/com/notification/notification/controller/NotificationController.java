package com.notification.notification.controller;

import com.shared2.classes.Notification;
import com.shared2.classes.NotificationBody;
import com.notification.notification.model.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationBody notificationBody) {
        Notification notification = new Notification(
                notificationBody.getPesel(), notificationBody.getEmail(), notificationBody.getMessage()
        );
        notificationRepository.saveAndFlush(notification);
        return ResponseEntity.created(null).body(notification);
    }
}
