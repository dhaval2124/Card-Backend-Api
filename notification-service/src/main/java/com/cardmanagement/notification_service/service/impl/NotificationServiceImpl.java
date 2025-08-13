package com.cardmanagement.notification_service.service.impl;

import com.cardmanagement.notification_service.dto.NotificationEvent;
import com.cardmanagement.notification_service.mail.MailService;
import com.cardmanagement.notification_service.model.Notification;
import com.cardmanagement.notification_service.model.NotificationStatus;
import com.cardmanagement.notification_service.repository.NotificationRepository;
import com.cardmanagement.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailService mailService;

    @Override
    public void processNotificationEvent(NotificationEvent event) {
        logger.info("Processing notification event: {}", event);

        String message = generateMessage(event);
        String subject = generateSubject(event);

        Notification notification = createNotification(
                event.getCustomerId(),
                "EMAIL",
                event.getEventType(),
                event.getEmail(),
                message,
                subject
        );

        sendNotification(notification);
    }

    @Override
    public Notification createNotification(String customerId, String type, String channel,
                                           String recipient, String message, String subject) {
        Notification notification = new Notification(customerId, type, channel, recipient, message, subject);
        return notificationRepository.save(notification);
    }

    @Override
    public void sendNotification(Notification notification) {
        try {
            if ("EMAIL".equals(notification.getType())) {
                mailService.sendEmail(
                        notification.getRecipient(),
                        notification.getSubject(),
                        notification.getMessage()
                );
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            logger.info("Notification sent successfully to: {}", notification.getRecipient());

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage(e.getMessage());
            logger.error("Failed to send notification: {}", e.getMessage());
        }

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByCustomer(String customerId) {
        return notificationRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    @Override
    public List<Notification> getPendingNotifications() {
        return notificationRepository.findByStatus(NotificationStatus.PENDING);
    }

    @Override
    public void retryFailedNotifications() {
        List<Notification> failedNotifications = notificationRepository.findByStatus(NotificationStatus.FAILED);
        for (Notification notification : failedNotifications) {
            notification.setStatus(NotificationStatus.RETRY);
            sendNotification(notification);
        }
    }

    private String generateMessage(NotificationEvent event) {
        return switch (event.getEventType()) {
            case "CARD_ISSUED" -> "Dear Customer, your new card has been issued successfully. Card details will be sent separately.";
            case "CARD_ACTIVATED" -> "Dear Customer, your card has been activated successfully and is ready to use.";
            case "TRANSACTION_ALERT" -> "Transaction Alert: A transaction has been processed on your card.";
            default -> "You have a new notification from Card Management System.";
        };
    }

    private String generateSubject(NotificationEvent event) {
        return switch (event.getEventType()) {
            case "CARD_ISSUED" -> "Card Issued Successfully";
            case "CARD_ACTIVATED" -> "Card Activated";
            case "TRANSACTION_ALERT" -> "Transaction Alert";
            default -> "Card Management Notification";
        };
    }
}
