package com.cardmanagement.notification_service.service;

import com.cardmanagement.notification_service.dto.NotificationEvent;
import com.cardmanagement.notification_service.model.Notification;

import java.util.List;

public interface NotificationService {
    void processNotificationEvent(NotificationEvent event);
    Notification createNotification(String customerId, String type, String channel,
                                    String recipient, String message, String subject);
    void sendNotification(Notification notification);
    List<Notification> getNotificationsByCustomer(String customerId);
    List<Notification> getPendingNotifications();
    void retryFailedNotifications();
}
