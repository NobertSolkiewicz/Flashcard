package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.Flashcard;
import com.kodilla.Flashcard.repository.FlashcardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlashcardServiceTest {

    @Mock
    private FlashcardRepository flashcardRepository;

    @InjectMocks
    private FlashcardService flashcardService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFlashcards() {
        // Given
        Deck deck = new Deck();
        Flashcard flashcard1 = new Flashcard(1L, "Question 1", "Answer 1", deck, new ArrayList<>());
        Flashcard flashcard2 = new Flashcard(2L, "Question 2", "Answer 2", deck, new ArrayList<>());
        when(flashcardRepository.findAll()).thenReturn(Arrays.asList(flashcard1, flashcard2));

        // When
        List<Flashcard> flashcards = flashcardService.getAllFlashcard();

        // Then
        assertEquals(2, flashcards.size());
        assertEquals("Question 1", flashcards.get(0).getQuestion());
        assertEquals("Answer 2", flashcards.get(1).getAnswer());
    }

    @Test
    public void testGetFlashcardById() {
        // Given
        Deck deck = new Deck();
        Flashcard flashcard = new Flashcard(1L, "Question", "Answer", deck, new ArrayList<>());
        when(flashcardRepository.findById(1L)).thenReturn(Optional.of(flashcard));

        // When
        Flashcard retrievedFlashcard = flashcardService.getFlashcardById(1L);

        // Then
        assertNotNull(retrievedFlashcard);
        assertEquals("Question", retrievedFlashcard.getQuestion());
        assertEquals("Answer", retrievedFlashcard.getAnswer());
    }

    @Test
    public void testGetFlashcardByIdFlashcardNotFound() {
        // Given
        when(flashcardRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(NoSuchElementException.class, () -> flashcardService.getFlashcardById(1L));
    }

    @Test
    public void testSaveFlashcard() {
        // Given
        Deck deck = new Deck();
        Flashcard flashcard = new Flashcard(1L, "Question", "Answer", deck, new ArrayList<>());
        when(flashcardRepository.save(flashcard)).thenReturn(flashcard);

        // When
        Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);

        // Then
        assertNotNull(savedFlashcard);
        assertEquals("Question", savedFlashcard.getQuestion());
        assertEquals("Answer", savedFlashcard.getAnswer());
    }

    @Test
    public void testDeleteFlashcard() {
        // Given
        doNothing().when(flashcardRepository).deleteById(1L);

        // When
        flashcardService.deleteFlashcard(1L);

        // Then
        verify(flashcardRepository, times(1)).deleteById(1L);
    }
}
