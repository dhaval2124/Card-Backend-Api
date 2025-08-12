package com.cardmanagement.payment_service.controller;

import com.cardmanagement.payment_service.dto.PaymentRequest;
import com.cardmanagement.payment_service.dto.PaymentResponse;
import com.cardmanagement.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentByTransactionId(@PathVariable String transactionId) {
        PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCardId(@PathVariable Long cardId) {
        List<PaymentResponse> responses = paymentService.getPaymentsByCardId(cardId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentResponse> responses = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/card/{cardId}/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCardIdAndUserId(
            @PathVariable Long cardId, @PathVariable Long userId) {
        List<PaymentResponse> responses = paymentService.getPaymentsByCardIdAndUserId(cardId, userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(@PathVariable String status) {
        List<PaymentResponse> responses = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/type/{transactionType}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByTransactionType(@PathVariable String transactionType) {
        List<PaymentResponse> responses = paymentService.getPaymentsByTransactionType(transactionType);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/card/{cardId}/daterange")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByDateRange(
            @PathVariable Long cardId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentResponse> responses = paymentService.getPaymentsByDateRange(cardId, startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/daterange")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentResponse> responses = paymentService.getPaymentsByUserIdAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/merchant/{merchantName}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByMerchant(@PathVariable String merchantName) {
        List<PaymentResponse> responses = paymentService.getPaymentsByMerchant(merchantName);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id, @RequestParam String status) {
        PaymentResponse response = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> responses = paymentService.getAllPayments();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}