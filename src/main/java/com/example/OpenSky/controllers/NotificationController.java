package com.example.OpenSky.controllers;

import com.example.OpenSky.entities.Notification;
import com.example.OpenSky.requests.Notification.NotificationRequest;
import com.example.OpenSky.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationRequest req) {
        Notification notification = notificationService.create(req);
        return ResponseEntity.ok(notification);
    }

    @Operation(summary = "Lấy thông báo của người dùng",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<List<Notification>> getUserNotifications(HttpServletRequest request) {
        String AuthHeader = request.getHeader("Authorization");
        String token = AuthHeader.replace("Bearer ", "");
        List<Notification> notifications = notificationService.findByUserId(token);
        return ResponseEntity.ok(notifications);
    }
}
