package com.cardmanagement.fraud_detection_service.controller;

import com.cardmanagement.fraud_detection_service.model.FraudAlert;
import com.cardmanagement.fraud_detection_service.service.FraudService;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud")
@CrossOrigin(origins = "*")
public class FraudController {

    @Autowired
    private FraudService fraudService;

    @PostMapping("/analyze")
    public ResponseEntity<FraudAlert> analyzeTransaction(@RequestBody String transactionData) {
        try {
            FraudAlert result = fraudService.analyzeFraud(transactionData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<FraudAlert>> getAllFraudAlerts() {
        List<FraudAlert> alerts = fraudService.getAllFraudAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/alerts/{id}")
    public ResponseEntity<FraudAlert> getFraudAlertById(@PathVariable Long id) {
        FraudAlert alert = fraudService.getFraudAlertById(id);
        if (alert != null) {
            return ResponseEntity.ok(alert);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/alerts/card/{cardNumber}")
    public ResponseEntity<List<FraudAlert>> getFraudAlertsByCardNumber(@PathVariable String cardNumber) {
        List<FraudAlert> alerts = fraudService.getFraudAlertsByCardNumber(cardNumber);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/alerts/risk/{riskLevel}")
    public ResponseEntity<List<FraudAlert>> getFraudAlertsByRiskLevel(@PathVariable String riskLevel) {
        List<FraudAlert> alerts = fraudService.getFraudAlertsByRiskLevel(riskLevel);
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/alerts/{id}/status")
    public ResponseEntity<FraudAlert> updateFraudAlertStatus(@PathVariable Long id, @RequestBody String status) {
        FraudAlert updatedAlert = fraudService.updateFraudAlertStatus(id, status);
        if (updatedAlert != null) {
            return ResponseEntity.ok(updatedAlert);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<Void> deleteFraudAlert(@PathVariable Long id) {
        fraudService.deleteFraudAlert(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Fraud Detection Service is running!");
    }
}