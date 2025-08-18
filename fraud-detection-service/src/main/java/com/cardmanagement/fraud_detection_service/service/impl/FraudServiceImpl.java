package com.cardmanagement.fraud_detection_service.service.impl;

import com.cardmanagement.fraud_detection_service.model.FraudAlert;
import com.cardmanagement.fraud_detection_service.repository.FraudRepository;
import com.cardmanagement.fraud_detection_service.service.FraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FraudServiceImpl implements FraudService {

    @Autowired
    private FraudRepository fraudRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String FRAUD_ALERT_TOPIC = "fraud-alerts";

    @Override
    public FraudAlert analyzeFraud(String transactionData) {
        // Parse transaction data (simplified)
        String[] data = transactionData.split(",");
        String transactionId = data[0];
        String cardNumber = data[1];
        BigDecimal amount = new BigDecimal(data[2]);
        String merchantName = data.length > 3 ? data[3] : "Unknown";

        // Simple fraud scoring logic
        BigDecimal fraudScore = calculateFraudScore(amount, cardNumber);
        String riskLevel = determineRiskLevel(fraudScore);
        String reason = generateReason(fraudScore, amount);

        FraudAlert alert = new FraudAlert();
        alert.setTransactionId(Long.valueOf(transactionId));
        alert.setCardNumber(cardNumber);
        alert.setTransactionAmount(amount);
        alert.setMerchantId(merchantName);
        alert.setRiskScore(fraudScore);
        alert.setReason(reason);

// convert string -> enum safely
        alert.setRiskLevel(FraudAlert.RiskLevel.valueOf(riskLevel));
        alert.setCreatedAt(LocalDateTime.now());

        FraudAlert savedAlert = fraudRepository.save(alert);

        // Send to Kafka if high risk
        if ("HIGH".equals(riskLevel) || "CRITICAL".equals(riskLevel)) {
            kafkaTemplate.send(FRAUD_ALERT_TOPIC, "Alert: " + savedAlert.getTransactionId() + " - " + riskLevel);
        }

        return savedAlert;
    }

    private BigDecimal calculateFraudScore(BigDecimal amount, String cardNumber) {
        Random random = new Random();
        double baseScore = 20.0;

        // Higher amounts increase fraud score
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            baseScore += 30.0;
        }
        if (amount.compareTo(new BigDecimal("5000")) > 0) {
            baseScore += 40.0;
        }

        // Add some randomness
        baseScore += random.nextDouble() * 20;

        return BigDecimal.valueOf(Math.min(baseScore, 100.0));
    }

    private String determineRiskLevel(BigDecimal fraudScore) {
        if (fraudScore.compareTo(new BigDecimal("80")) >= 0) {
            return "CRITICAL";
        } else if (fraudScore.compareTo(new BigDecimal("60")) >= 0) {
            return "HIGH";
        } else if (fraudScore.compareTo(new BigDecimal("40")) >= 0) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    private String generateReason(BigDecimal fraudScore, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("5000")) > 0) {
            return "High amount transaction detected";
        } else if (fraudScore.compareTo(new BigDecimal("70")) >= 0) {
            return "Suspicious transaction pattern";
        } else {
            return "Normal transaction";
        }
    }

    @Override
    public List<FraudAlert> getAllFraudAlerts() {
        return fraudRepository.findAll();
    }

    @Override
    public FraudAlert getFraudAlertById(Long id) {
        return fraudRepository.findById(id).orElse(null);
    }

    @Override
    public List<FraudAlert> getFraudAlertsByCardNumber(String cardNumber) {
        return fraudRepository.findByCardNumber(cardNumber);
    }

    @Override
    public List<FraudAlert> getFraudAlertsByRiskLevel(String riskLevel) {
        try {
            FraudAlert.RiskLevel level = FraudAlert.RiskLevel.valueOf(riskLevel.toUpperCase());
            return fraudRepository.findByRiskLevel(level);
        } catch (IllegalArgumentException e) {
            return List.of(); // return empty if invalid string
        }
    }

    @Override
    public FraudAlert updateFraudAlertStatus(Long id, String status) {
        FraudAlert alert = fraudRepository.findById(id).orElse(null);
        if (alert != null) {
            alert.setAlertStatus(FraudAlert.AlertStatus.valueOf(status.toUpperCase()));
            alert.setUpdatedAt(LocalDateTime.now());
            return fraudRepository.save(alert);
        }
        return null;
    }

    @Override
    public void deleteFraudAlert(Long id) {
        fraudRepository.deleteById(id);
    }
}