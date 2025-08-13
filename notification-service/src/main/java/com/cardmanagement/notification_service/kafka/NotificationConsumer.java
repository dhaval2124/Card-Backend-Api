package com.cardmanagement.notification_service.kafka;

import com.cardmanagement.notification_service.dto.NotificationEvent;
import com.cardmanagement.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "#{@environment.getProperty('notification.topics.card-issued')}")
    public void handleCardIssuedEvent(@Payload NotificationEvent event,
                                      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                      @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                      @Header(KafkaHeaders.OFFSET) long offset,
                                      Acknowledgment acknowledgment) {

        logger.info("Received card issued event from topic: {}, partition: {}, offset: {}", topic, partition, offset);
        logger.info("Event details: {}", event);

        try {
            notificationService.processNotificationEvent(event);
            acknowledgment.acknowledge();
            logger.info("Card issued notification processed successfully");
        } catch (Exception e) {
            logger.error("Error processing card issued event: {}", e.getMessage(), e);
            // Handle error - could implement retry logic here
        }
    }

    @KafkaListener(topics = "#{@environment.getProperty('notification.topics.card-activated')}")
    public void handleCardActivatedEvent(@Payload NotificationEvent event,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                         Acknowledgment acknowledgment) {

        logger.info("Received card activated event from topic: {}", topic);
        logger.info("Event details: {}", event);

        try {
            notificationService.processNotificationEvent(event);
            acknowledgment.acknowledge();
            logger.info("Card activated notification processed successfully");
        } catch (Exception e) {
            logger.error("Error processing card activated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "#{@environment.getProperty('notification.topics.transaction-alert')}")
    public void handleTransactionAlertEvent(@Payload NotificationEvent event,
                                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                            Acknowledgment acknowledgment) {

        logger.info("Received transaction alert event from topic: {}", topic);
        logger.info("Event details: {}", event);

        try {
            notificationService.processNotificationEvent(event);
            acknowledgment.acknowledge();
            logger.info("Transaction alert notification processed successfully");
        } catch (Exception e) {
            logger.error("Error processing transaction alert event: {}", e.getMessage(), e);
        }
    }
}
