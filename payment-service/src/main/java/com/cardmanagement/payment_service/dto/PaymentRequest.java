package com.cardmanagement.payment_service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentRequest {
    @NotNull(message = "Card ID is required")
    private Long cardId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    @NotBlank(message = "Transaction type is required")
    @Pattern(regexp = "DEBIT|CREDIT|REFUND", message = "Transaction type must be DEBIT, CREDIT, or REFUND")
    private String transactionType;

    private String merchantName;
    private String description;

    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(Long cardId, Long userId, BigDecimal amount,
                          String transactionType, String merchantName, String description) {
        this.cardId = cardId;
        this.userId = userId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.merchantName = merchantName;
        this.description = description;
    }

    // Getters and Setters
    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
