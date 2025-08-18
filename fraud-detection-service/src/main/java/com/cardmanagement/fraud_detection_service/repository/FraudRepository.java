package com.cardmanagement.fraud_detection_service.repository;

import com.cardmanagement.fraud_detection_service.model.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FraudRepository extends JpaRepository<FraudAlert, Long> {

    List<FraudAlert> findByCardNumber(String cardNumber);

    List<FraudAlert> findByRiskLevel(FraudAlert.RiskLevel riskLevel);

//    List<FraudAlert> findByAlertStatus(FraudAlert.AlertStatus status);

    @Query("SELECT f FROM FraudAlert f WHERE f.riskScore >= :minScore")
    List<FraudAlert> findByRiskScoreGreaterThanEqual(@Param("minScore") BigDecimal minScore);
}