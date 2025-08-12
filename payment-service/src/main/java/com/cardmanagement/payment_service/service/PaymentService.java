package com.cardmanagement.payment_service.service;

import com.cardmanagement.payment_service.dto.PaymentRequest;
import com.cardmanagement.payment_service.dto.PaymentResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentById(Long id);
    PaymentResponse getPaymentByTransactionId(String transactionId);
    List<PaymentResponse> getPaymentsByCardId(Long cardId);
    List<PaymentResponse> getPaymentsByUserId(Long userId);
    List<PaymentResponse> getPaymentsByCardIdAndUserId(Long cardId, Long userId);
    List<PaymentResponse> getPaymentsByStatus(String status);
    List<PaymentResponse> getPaymentsByTransactionType(String transactionType);
    List<PaymentResponse> getPaymentsByDateRange(Long cardId, LocalDateTime startDate, LocalDateTime endDate);
    List<PaymentResponse> getPaymentsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<PaymentResponse> getPaymentsByMerchant(String merchantName);
    PaymentResponse updatePaymentStatus(Long id, String status);
    List<PaymentResponse> getAllPayments();
    void deletePayment(Long id);
}
