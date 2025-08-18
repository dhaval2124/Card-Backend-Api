package com.cardmanagement.fraud_detection_service.service;

import com.cardmanagement.fraud_detection_service.model.FraudAlert;

import java.util.List;

public interface FraudService {
    FraudAlert analyzeFraud(String transactionData);
    List<FraudAlert> getAllFraudAlerts();
    FraudAlert getFraudAlertById(Long id);
    List<FraudAlert> getFraudAlertsByCardNumber(String cardNumber);
    List<FraudAlert> getFraudAlertsByRiskLevel(String riskLevel);
    FraudAlert updateFraudAlertStatus(Long id, String status);
    void deleteFraudAlert(Long id);
}