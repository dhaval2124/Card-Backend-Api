package com.cardmanagement.card_service.service.impl;

import com.cardmanagement.card_service.dto.CardRequest;
import com.cardmanagement.card_service.dto.CardResponse;
import com.cardmanagement.card_service.exception.CardNotFoundException;
import com.cardmanagement.card_service.model.Card;
import com.cardmanagement.card_service.repository.CardRepository;
import com.cardmanagement.card_service.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardResponse createCard(CardRequest cardRequest) {
        if (cardRepository.existsByCardNumber(cardRequest.getCardNumber())) {
            throw new RuntimeException("Card with this number already exists");
        }

        Card card = new Card();
        mapRequestToEntity(cardRequest, card);
        card.setStatus(Card.CardStatus.ACTIVE);
        card.setCreatedDate(LocalDateTime.now());

        Card savedCard = cardRepository.save(card);
        return mapEntityToResponse(savedCard);
    }

    @Override
    public CardResponse getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
        return mapEntityToResponse(card);
    }

    @Override
    public CardResponse getCardByNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException("Card not found with number: " + cardNumber));
        return mapEntityToResponse(card);
    }

    @Override
    public List<CardResponse> getCardsByCustomerId(Long customerId) {
        List<Card> cards = cardRepository.findByCustomerId(customerId);
        return cards.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CardResponse updateCard(Long id, CardRequest cardRequest) {
        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));

        mapRequestToEntity(cardRequest, existingCard);
        existingCard.setUpdatedDate(LocalDateTime.now());

        Card updatedCard = cardRepository.save(existingCard);
        return mapEntityToResponse(updatedCard);
    }

    @Override
    public void deleteCard(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new CardNotFoundException("Card not found with id: " + id);
        }
        cardRepository.deleteById(id);
    }

    @Override
    public CardResponse blockCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));

        card.setStatus(Card.CardStatus.BLOCKED);
        card.setUpdatedDate(LocalDateTime.now());

        Card updatedCard = cardRepository.save(card);
        return mapEntityToResponse(updatedCard);
    }

    @Override
    public CardResponse activateCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));

        card.setStatus(Card.CardStatus.ACTIVE);
        card.setUpdatedDate(LocalDateTime.now());

        Card updatedCard = cardRepository.save(card);
        return mapEntityToResponse(updatedCard);
    }

    private void mapRequestToEntity(CardRequest request, Card card) {
        card.setCardNumber(request.getCardNumber());
        card.setCardHolderName(request.getCardHolderName());
        card.setCustomerId(request.getCustomerId());
        card.setCardType(request.getCardType());
        card.setExpiryDate(request.getExpiryDate());
        card.setCvv(request.getCvv());
        card.setCreditLimit(request.getCreditLimit());
        card.setAvailableBalance(request.getAvailableBalance());
    }

    private CardResponse mapEntityToResponse(Card card) {
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setCardNumber(card.getCardNumber());
        response.setCardHolderName(card.getCardHolderName());
        response.setCustomerId(card.getCustomerId());
        response.setCardType(card.getCardType());
        response.setExpiryDate(card.getExpiryDate());
        response.setCreditLimit(card.getCreditLimit());
        response.setAvailableBalance(card.getAvailableBalance());
        response.setStatus(card.getStatus());
        response.setCreatedDate(card.getCreatedDate());
        response.setUpdatedDate(card.getUpdatedDate());
        return response;
    }
}