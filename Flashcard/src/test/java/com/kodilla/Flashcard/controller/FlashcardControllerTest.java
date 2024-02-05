package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.Flashcard;
import com.kodilla.Flashcard.domain.FlashcardDto;
import com.kodilla.Flashcard.mapper.FlashcardMapper;
import com.kodilla.Flashcard.service.FlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FlashcardControllerTest {

    @InjectMocks
    private FlashcardController flashcardController;

    @Mock
    private FlashcardService flashcardService;

    @Mock
    private FlashcardMapper flashcardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetFlashcards() {
        //Given
        Flashcard flashcard = new Flashcard();
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Test Question", "Test Answer", new Deck() , new ArrayList<>());
        when(flashcardService.getAllFlashcard()).thenReturn(Collections.singletonList(flashcard));
        when(flashcardMapper.mapToFlashcardDtoList(Collections.singletonList(flashcard))).thenReturn(Collections.singletonList(flashcardDto));

        //When
        ResponseEntity<List<FlashcardDto>> response = flashcardController.getFlashcards();

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(flashcardDto), response.getBody());
    }

    @Test
    public void shouldGetFlashcard() {
        //Given
        Long flashcardId = 1L;
        Flashcard flashcard = new Flashcard();
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Test Question", "Test Answer", new Deck() , new ArrayList<>());
        when(flashcardService.getFlashcardById(flashcardId)).thenReturn(flashcard);
        when(flashcardMapper.mapToFlashcardDto(flashcard)).thenReturn(flashcardDto);

        //When
        ResponseEntity<FlashcardDto> response = flashcardController.getFlashcardById(flashcardId);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flashcardDto, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenFlashcardNotFound() {
        //Given
        Long flashcardId = 1L;
        when(flashcardService.getFlashcardById(flashcardId)).thenReturn(null);

        // When
        ResponseEntity<FlashcardDto> response = flashcardController.getFlashcardById(flashcardId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldDeleteFlashcard() {
        //Given
        Long flashcardId = 1L;

        //When
        ResponseEntity<Void> response = flashcardController.deleteFlashcard(flashcardId);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(flashcardService, times(1)).deleteFlashcard(flashcardId);
    }

    @Test
    public void shouldUpdateFlashcard() {
        //Given
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Test Question", "Test Answer", new Deck() , new ArrayList<>());
        Flashcard flashcard = new Flashcard();
        when(flashcardMapper.mapToFlashcard(flashcardDto)).thenReturn(flashcard);
        when(flashcardService.saveFlashcard(flashcard)).thenReturn(flashcard);

        //When
        ResponseEntity<FlashcardDto> response = flashcardController.updateFlashcard(flashcardDto);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(flashcardService, times(1)).saveFlashcard(flashcard);
        verify(flashcardMapper, times(1)).mapToFlashcardDto(flashcard);
    }

    @Test
    public void shouldCreateFlashcard() {
        //Given
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Test Question", "Test Answer", new Deck() , new ArrayList<>());
        Flashcard flashcard = new Flashcard();
        when(flashcardMapper.mapToFlashcard(flashcardDto)).thenReturn(flashcard);

        //When
        ResponseEntity<Void> response = flashcardController.createFlashcard(flashcardDto);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(flashcardService, times(1)).saveFlashcard(flashcard);
    }

}
