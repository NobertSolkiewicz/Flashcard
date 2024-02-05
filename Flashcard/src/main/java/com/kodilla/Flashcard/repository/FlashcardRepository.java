package com.kodilla.Flashcard.repository;

import com.kodilla.Flashcard.domain.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    @Override
   List<Flashcard> findAll();

    @Override
    Flashcard save(Flashcard flashcard);

    @Override
    Optional<Flashcard> findById(Long id);

    @Override
    void deleteById(Long id);
}
