package com.cardmanagement.card_service.service;

import com.cardmanagement.card_service.dto.CardRequest;
import com.cardmanagement.card_service.dto.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse createCard(CardRequest cardRequest);
    CardResponse getCardById(Long id);
    CardResponse getCardByNumber(String cardNumber);
    List<CardResponse> getCardsByCustomerId(Long customerId);
    List<CardResponse> getAllCards();
    CardResponse updateCard(Long id, CardRequest cardRequest);
    void deleteCard(Long id);
    CardResponse blockCard(Long id);
    CardResponse activateCard(Long id);
}