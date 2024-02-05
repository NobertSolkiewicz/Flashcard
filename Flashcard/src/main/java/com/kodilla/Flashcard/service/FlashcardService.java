package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.Flashcard;
import com.kodilla.Flashcard.repository.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    public List<Flashcard> getAllFlashcard() {
        return flashcardRepository.findAll();
    }

    public Flashcard getFlashcardById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Flashcard not found"));
    }

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }
}
