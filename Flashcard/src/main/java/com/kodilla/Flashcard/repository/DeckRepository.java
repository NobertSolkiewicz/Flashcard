package com.kodilla.Flashcard.repository;

import com.kodilla.Flashcard.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}
