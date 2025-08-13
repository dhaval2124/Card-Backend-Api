package com.cardmanagement.notification_service.mail;

public interface MailService {
    void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody);
}