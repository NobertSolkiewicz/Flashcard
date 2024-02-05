package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.Flashcard;
import com.kodilla.Flashcard.domain.FlashcardDto;
import com.kodilla.Flashcard.mapper.FlashcardMapper;
import com.kodilla.Flashcard.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    private final FlashcardMapper flashcardMapper;

    @GetMapping
    public ResponseEntity<List<FlashcardDto>> getFlashcards() {
        List<Flashcard> flashcards = flashcardService.getAllFlashcard();
        return ResponseEntity.ok(flashcardMapper.mapToFlashcardDtoList(flashcards));
    }

    @GetMapping(value = "{flashcardId}")
    public ResponseEntity<FlashcardDto> getFlashcardById (@PathVariable Long flashcardId) {
        Flashcard flashcard = flashcardService.getFlashcardById(flashcardId);

        if (flashcard == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(flashcardMapper.mapToFlashcardDto(flashcard));
    }

    @DeleteMapping(value = "{flashcardId}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long flashcardId) {
        flashcardService.deleteFlashcard(flashcardId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<FlashcardDto> updateFlashcard(@RequestBody FlashcardDto flashcardDto) {
        Flashcard flashcard = flashcardMapper.mapToFlashcard(flashcardDto);
        Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);
        return ResponseEntity.ok(flashcardMapper.mapToFlashcardDto(savedFlashcard));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createFlashcard(@RequestBody FlashcardDto flashcardDto) {
        Flashcard flashcard = flashcardMapper.mapToFlashcard(flashcardDto);
        flashcardService.saveFlashcard(flashcard);
        return ResponseEntity.ok().build();
    }
}
