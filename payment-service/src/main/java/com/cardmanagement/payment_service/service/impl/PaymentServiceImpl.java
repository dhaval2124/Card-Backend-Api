package com.cardmanagement.payment_service.service.impl;

import com.cardmanagement.payment_service.dto.PaymentRequest;
import com.cardmanagement.payment_service.dto.PaymentResponse;
import com.cardmanagement.payment_service.feign.UserClient;
import com.cardmanagement.payment_service.model.Payment;
import com.cardmanagement.payment_service.repository.PaymentRepository;
import com.cardmanagement.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Validate user exists (optional - using Feign client)
        // COMMENTED OUT TO AVOID DEPENDENCY ON USER SERVICE
        /*
        try {
            userClient.getUserById(paymentRequest.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("User not found with ID: " + paymentRequest.getUserId());
        }
        */

        // Optional: Add graceful user validation
        validateUserGracefully(paymentRequest.getUserId());

        // Generate unique transaction ID
        String transactionId = generateTransactionId();

        // Create payment entity
        Payment payment = new Payment(
                paymentRequest.getCardId(),
                paymentRequest.getUserId(),
                paymentRequest.getAmount(),
                paymentRequest.getTransactionType(),
                paymentRequest.getMerchantName(),
                paymentRequest.getDescription(),
                "PENDING", // Initial status
                transactionId
        );

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Process payment logic (simulate success/failure)
        String finalStatus = processPaymentLogic(payment);
        savedPayment.setStatus(finalStatus);
        savedPayment = paymentRepository.save(savedPayment);

        return convertToResponse(savedPayment);
    }

    /**
     * Optional graceful user validation that doesn't fail the payment process
     */
    private void validateUserGracefully(Long userId) {
        try {
            userClient.getUserById(userId);
            System.out.println("✅ User validation successful for ID: " + userId);
        } catch (Exception e) {
            System.out.println("⚠️ User service unavailable or user not found, proceeding without validation: " + e.getMessage());
            // Continue processing without failing
        }
    }

    private String processPaymentLogic(Payment payment) {
        // Simulate payment processing logic
        // In real scenario, you would integrate with payment gateways
        try {
            Thread.sleep(100); // Simulate processing time
            // 90% success rate for simulation
            return Math.random() > 0.1 ? "SUCCESS" : "FAILED";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            return "FAILED";
        }
    }

    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        return convertToResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found with transaction ID: " + transactionId));
        return convertToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByCardId(Long cardId) {
        return paymentRepository.findByCardId(cardId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByCardIdAndUserId(Long cardId, Long userId) {
        return paymentRepository.findByCardIdAndUserId(cardId, userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByTransactionType(String transactionType) {
        return paymentRepository.findByTransactionType(transactionType).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByDateRange(Long cardId, LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByCardIdAndDateRange(cardId, startDate, endDate).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByUserIdAndDateRange(userId, startDate, endDate).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByMerchant(String merchantName) {
        return paymentRepository.findByMerchantNameContaining(merchantName).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        payment.setStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        return convertToResponse(updatedPayment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private PaymentResponse convertToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getCardId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getTransactionType(),
                payment.getMerchantName(),
                payment.getDescription(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
