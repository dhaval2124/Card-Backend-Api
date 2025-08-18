package com.cardmanagement.fraud_detection_service.kafka;

import com.cardmanagement.fraud_detection_service.service.FraudService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class FraudConsumer {

    @Autowired
    private FraudService fraudService;

    @KafkaListener(topics = "transaction-events", groupId = "fraud-group")
    public void consume(String transactionData) {
        System.out.println("Received transaction for analysis: " + transactionData);
        try {
            fraudService.analyzeFraud(transactionData);
            System.out.println("Transaction analyzed successfully");
        } catch (Exception e) {
            System.err.println("Error processing transaction: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "fraud-alerts", groupId = "fraud-group")
    public void consumeFraudAlerts(String alertMessage) {
        System.out.println("Fraud Alert: " + alertMessage);
    }
}