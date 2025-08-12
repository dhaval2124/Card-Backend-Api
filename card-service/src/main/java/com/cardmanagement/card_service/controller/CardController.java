package com.cardmanagement.card_service.controller;

import com.cardmanagement.card_service.dto.CardRequest;
import com.cardmanagement.card_service.dto.CardResponse;
import com.cardmanagement.card_service.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest cardRequest) {
        CardResponse cardResponse = cardService.createCard(cardRequest);
        return new ResponseEntity<>(cardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long id) {
        CardResponse cardResponse = cardService.getCardById(id);
        return ResponseEntity.ok(cardResponse);
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardResponse> getCardByNumber(@PathVariable String cardNumber) {
        CardResponse cardResponse = cardService.getCardByNumber(cardNumber);
        return ResponseEntity.ok(cardResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CardResponse>> getCardsByCustomerId(@PathVariable Long customerId) {
        List<CardResponse> cards = cardService.getCardsByCustomerId(customerId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards() {
        List<CardResponse> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long id,
                                                   @Valid @RequestBody CardRequest cardRequest) {
        CardResponse cardResponse = cardService.updateCard(id, cardRequest);
        return ResponseEntity.ok(cardResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<CardResponse> blockCard(@PathVariable Long id) {
        CardResponse cardResponse = cardService.blockCard(id);
        return ResponseEntity.ok(cardResponse);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<CardResponse> activateCard(@PathVariable Long id) {
        CardResponse cardResponse = cardService.activateCard(id);
        return ResponseEntity.ok(cardResponse);
    }
}