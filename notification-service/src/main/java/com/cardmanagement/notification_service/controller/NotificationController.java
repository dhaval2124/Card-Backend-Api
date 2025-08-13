package com.cardmanagement.notification_service.controller;

import com.cardmanagement.notification_service.dto.NotificationEvent;
import com.cardmanagement.notification_service.model.Notification;
import com.cardmanagement.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody NotificationEvent event) {
        try {
            logger.info("Received notification request: {}", event);
            notificationService.processNotificationEvent(event);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Notification sent successfully"
            ));
        } catch (Exception e) {
            logger.error("Error sending notification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Failed to send notification: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Notification>> getCustomerNotifications(@PathVariable String customerId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByCustomer(customerId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            logger.error("Error fetching notifications for customer {}: {}", customerId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Notification>> getPendingNotifications() {
        try {
            List<Notification> notifications = notificationService.getPendingNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            logger.error("Error fetching pending notifications: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/retry-failed")
    public ResponseEntity<Map<String, String>> retryFailedNotifications() {
        try {
            notificationService.retryFailedNotifications();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Failed notifications retry initiated"
            ));
        } catch (Exception e) {
            logger.error("Error retrying failed notifications: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Failed to retry notifications: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "notification-service",
                "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
}
