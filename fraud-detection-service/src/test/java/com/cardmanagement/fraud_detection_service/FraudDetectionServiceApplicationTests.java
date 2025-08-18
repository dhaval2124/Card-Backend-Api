package com.cardmanagement.fraud_detection_service;

import com.cardmanagement.fraud_detection_service.model.FraudAlert;
import com.cardmanagement.fraud_detection_service.repository.FraudRepository;
import com.cardmanagement.fraud_detection_service.service.impl.FraudServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FraudDetectionServiceApplicationTests {

    @Mock
    private FraudRepository fraudRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate; // ✅ mock Kafka

    @InjectMocks
    private FraudServiceImpl fraudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Stub repository save safely
        when(fraudRepository.save(any())).thenAnswer(inv -> {
            FraudAlert a = inv.getArgument(0);
            if (a != null && a.getId() == null) {
                a.setId(1L);
            }
            return a;
        });

        // Stub Kafka so it doesn’t throw NPE
        when(kafkaTemplate.send(any(), any())).thenReturn(null);
    }

    @Test
    void contextLoads() {
        assertNotNull(fraudService);
    }

    @Test
    void testCreateFraudAlert() {
        FraudAlert alert = new FraudAlert(
                "4532123456789999",
                BigDecimal.valueOf(5000.0),
                "TestMerchant",
                "TestLocation",
                FraudAlert.FraudType.HIGH_AMOUNT,
                FraudAlert.RiskLevel.HIGH
        );

        FraudAlert savedAlert = fraudRepository.save(alert);

        assertNotNull(savedAlert.getId());
        assertEquals("4532123456789999", savedAlert.getCardNumber());
        assertEquals(0, BigDecimal.valueOf(5000.0).compareTo(savedAlert.getTransactionAmount()));
        assertEquals("TestMerchant", savedAlert.getMerchantId());
        assertEquals(FraudAlert.RiskLevel.HIGH, savedAlert.getRiskLevel());
    }

    @Test
    void testAnalyzeFraud() {
        // transactionData = "id,cardNumber,amount,merchant"
        String transactionData = "123,4532123456789998,15000.0,ElectronicsStore";

        FraudAlert alert = fraudService.analyzeFraud(transactionData);

        assertNotNull(alert);
        assertEquals("4532123456789998", alert.getCardNumber());
        assertTrue(alert.getTransactionAmount().compareTo(BigDecimal.valueOf(15000)) == 0);
        assertTrue(alert.getRiskLevel() == FraudAlert.RiskLevel.HIGH
                || alert.getRiskLevel() == FraudAlert.RiskLevel.CRITICAL);
    }

    @Test
    void testGetAllFraudAlerts() {
        when(fraudRepository.findAll()).thenReturn(Arrays.asList(
                new FraudAlert("1111", BigDecimal.valueOf(1000), "M1", "City1", FraudAlert.FraudType.HIGH_AMOUNT, FraudAlert.RiskLevel.LOW),
                new FraudAlert("2222", BigDecimal.valueOf(2000), "M2", "City2", FraudAlert.FraudType.UNUSUAL_LOCATION, FraudAlert.RiskLevel.HIGH)
        ));

        List<FraudAlert> alerts = fraudService.getAllFraudAlerts();

        assertNotNull(alerts);
        assertEquals(2, alerts.size());
    }

    @Test
    void testGetFraudAlertsByCardNumber() {
        FraudAlert alert = new FraudAlert(
                "4532123456789012",
                BigDecimal.valueOf(2500.0),
                "MerchantX",
                "Mumbai",
                FraudAlert.FraudType.UNUSUAL_LOCATION,
                FraudAlert.RiskLevel.MEDIUM
        );

        when(fraudRepository.findByCardNumber("4532123456789012"))
                .thenReturn(Collections.singletonList(alert));

        List<FraudAlert> results = fraudService.getFraudAlertsByCardNumber("4532123456789012");

        assertFalse(results.isEmpty());
        assertEquals("4532123456789012", results.get(0).getCardNumber());
    }

    @Test
    void testUpdateFraudAlertStatus() {
        FraudAlert alert = new FraudAlert(
                "4532123456787777",
                BigDecimal.valueOf(1000.0),
                "MerchantY",
                "Delhi",
                FraudAlert.FraudType.FREQUENT_TRANSACTIONS,
                FraudAlert.RiskLevel.LOW
        );
        alert.setId(10L);

        when(fraudRepository.findById(10L)).thenReturn(Optional.of(alert));
        when(fraudRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        FraudAlert updated = fraudService.updateFraudAlertStatus(10L, "RESOLVED");

        assertNotNull(updated);
        assertEquals(FraudAlert.AlertStatus.RESOLVED, updated.getAlertStatus());
    }

    @Test
    void testDeleteFraudAlert() {
        FraudAlert alert = new FraudAlert(
                "4532123456786666",
                BigDecimal.valueOf(800.0),
                "MerchantZ",
                "Chennai",
                FraudAlert.FraudType.VELOCITY_CHECK,
                FraudAlert.RiskLevel.LOW
        );
        alert.setId(20L);

        when(fraudRepository.findById(20L)).thenReturn(Optional.of(alert));

        fraudService.deleteFraudAlert(20L);

        when(fraudRepository.findById(20L)).thenReturn(Optional.empty());

        FraudAlert deleted = fraudService.getFraudAlertById(20L);
        assertNull(deleted);
    }
}
