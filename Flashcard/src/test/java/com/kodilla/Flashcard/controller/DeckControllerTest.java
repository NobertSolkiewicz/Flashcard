package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.*;
import com.kodilla.Flashcard.mapper.DeckMapper;
import com.kodilla.Flashcard.service.DeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeckControllerTest {

    private DeckController deckController;
    private DeckService deckService;
    private DeckMapper deckMapper;

    @BeforeEach
    public void setUp() {
        deckService = mock(DeckService.class);
        deckMapper = mock(DeckMapper.class);
        deckController = new DeckController(deckService, deckMapper);
    }

    @Test
    public void shouldGetAllDecks() {
        //Given
        List<Deck> mockDecks = Collections.singletonList(new Deck());
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", new ArrayList<>(), new ArrayList<>(), new User());
        when(deckService.getAllDecks()).thenReturn(mockDecks);
        when(deckMapper.mapToDeckDtoList(mockDecks)).thenReturn(Collections.singletonList(deckDto));

        //When
        ResponseEntity<List<DeckDto>> response = deckController.getAllDecks();

        //Then
        assertEquals(200, response.getStatusCodeValue());
        verify(deckService, times(1)).getAllDecks();
    }

    @Test
    public void shouldGetDeckById() {
        //Given
        Long deckId = 1L;
        Deck mockDeck = new Deck();
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", new ArrayList<>(), new ArrayList<>(), new User());

        when(deckService.getDeckById(deckId)).thenReturn(mockDeck);
        when(deckMapper.mapToDeckDto(mockDeck)).thenReturn(deckDto);

        //When
        ResponseEntity<DeckDto> response = deckController.getDeck(deckId);

        //Then
        assertEquals(200, response.getStatusCodeValue());
        verify(deckService, times(1)).getDeckById(deckId);
    }

    @Test
    public void shouldReturnNotFoundWhenDeckNotFound() {
        //Given
        Long deckId = 1L;
        when(deckService.getDeckById(deckId)).thenReturn(null);

        //When
        ResponseEntity<DeckDto> response = deckController.getDeck(deckId);

        //Then
        assertEquals(404, response.getStatusCodeValue());
        verify(deckService, times(1)).getDeckById(deckId);
    }

    @Test
    public void shouldDeleteDeck() {
        //Given
        Long deckId = 1L;

        //When
        ResponseEntity<Void> response = deckController.deleteDeck(deckId);

        //Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deckService, times(1)).deleteDeck(deckId);
    }

    @Test
    public void shouldUpdateDeck() {
        // Arrange
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", new ArrayList<>(), new ArrayList<>(), new User());
        Deck deck = new Deck();
        when(deckMapper.mapToDeck(deckDto)).thenReturn(deck);
        when(deckService.saveDeck(deck)).thenReturn(deck);

        // Act
        ResponseEntity<DeckDto> response = deckController.updateDeck(deckDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deckService, times(1)).saveDeck(deck);
        verify(deckMapper, times(1)).mapToDeckDto(deck);
    }

    @Test
    public void shouldCreateDeck() {
        //Given
        Tag tag = new Tag(1L, "Test Tag", new ArrayList<>());
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", new ArrayList<>(), tags, new User());
        when(deckMapper.mapToDeck(deckDto)).thenReturn(new Deck());
        when(deckService.saveDeck(any(Deck.class))).thenReturn(new Deck());

        //When
        ResponseEntity<Void> response = deckController.createDeck(deckDto);

        //Then
        assertEquals(200, response.getStatusCodeValue());
        verify(deckService, times(1)).saveDeck(any(Deck.class));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidDeckData() {
        //Given
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", new ArrayList<>(), new ArrayList<>(), new User());

        //When
        ResponseEntity<Void> response = deckController.createDeck(deckDto);

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldAddTagToDeck() {
        //Given
        Long deckId = 1L;
        TagDto tagDto = new TagDto(1L, "Test Tag", new ArrayList<>());

        //When
        ResponseEntity<Void> response = deckController.addTagToDeck(deckId, tagDto);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deckService, times(1)).addTagToDeck(deckId, tagDto);
    }

    @Test
    public void shouldUpdateTagInDeck() {
        //Given
        Long deckId = 1L;
        Long tagId = 2L;
        TagDto tagDto = new TagDto(1L, "Test Tag", new ArrayList<>());

        //When
        ResponseEntity<Void> response = deckController.updateTagInDeck(deckId, tagId, tagDto);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deckService, times(1)).updateTagInDeck(deckId, tagId, tagDto);
    }

    @Test
    public void shouldRemoveTagFromDeck() {
        //Given
        Long deckId = 1L;
        Long tagId = 2L;

        //When
        ResponseEntity<Void> response = deckController.removeTagFromDeck(deckId, tagId);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deckService, times(1)).removeTagFromDeck(deckId, tagId);
    }
}
