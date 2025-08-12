package com.cardmanagement.card_service.repository;

import com.cardmanagement.card_service.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
    List<Card> findByCustomerId(Long customerId);
    List<Card> findByCardType(String cardType);
    List<Card> findByStatus(Card.CardStatus status);
    boolean existsByCardNumber(String cardNumber);
}