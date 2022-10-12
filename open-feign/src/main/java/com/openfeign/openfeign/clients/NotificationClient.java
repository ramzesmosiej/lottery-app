package com.openfeign.openfeign.clients;

import DTO.Notification;
import DTO.NotificationBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("notification")
public interface NotificationClient {
    @PostMapping("/notification")
    ResponseEntity<Notification> createNotification(@RequestBody NotificationBody notificationBody);
}
