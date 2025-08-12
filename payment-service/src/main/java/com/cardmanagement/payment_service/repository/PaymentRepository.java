package com.cardmanagement.payment_service.repository;

import com.cardmanagement.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCardId(Long cardId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByCardIdAndUserId(Long cardId, Long userId);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByStatus(String status);

    List<Payment> findByTransactionType(String transactionType);

    @Query("SELECT p FROM Payment p WHERE p.cardId = :cardId AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findByCardIdAndDateRange(@Param("cardId") Long cardId,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Payment p WHERE p.userId = :userId AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findByUserIdAndDateRange(@Param("userId") Long userId,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Payment p WHERE p.merchantName LIKE %:merchantName%")
    List<Payment> findByMerchantNameContaining(@Param("merchantName") String merchantName);
}
