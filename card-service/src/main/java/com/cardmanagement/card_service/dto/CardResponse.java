package com.cardmanagement.card_service.dto;

import com.cardmanagement.card_service.model.Card;

import java.time.LocalDateTime;

public class CardResponse {
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    private Long customerId;
    private String cardType;
    private String expiryDate;
    private Double creditLimit;
    private Double availableBalance;
    private Card.CardStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public Double getCreditLimit() { return creditLimit; }
    public void setCreditLimit(Double creditLimit) { this.creditLimit = creditLimit; }

    public Double getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(Double availableBalance) { this.availableBalance = availableBalance; }

    public Card.CardStatus getStatus() { return status; }
    public void setStatus(Card.CardStatus status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
