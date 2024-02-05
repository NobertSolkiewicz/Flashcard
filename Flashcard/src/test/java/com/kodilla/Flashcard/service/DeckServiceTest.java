package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.domain.TagDto;
import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.repository.DeckRepository;
import com.kodilla.Flashcard.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DeckServiceTest {

    @InjectMocks
    private DeckService deckService;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDecks() {
        // Given
        List<Deck> decks = new ArrayList<>();
        User user = new User();
        decks.add(new Deck(1L, "Deck 1", "Description 1", new ArrayList<>(), new ArrayList<>(), user));
        decks.add(new Deck(2L, "Deck 2", "Description 2", new ArrayList<>(), new ArrayList<>(), user));
        when(deckRepository.findAll()).thenReturn(decks);

        // When
        List<Deck> result = deckService.getAllDecks();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    public void testGetDeckById() {
        // Given
        User user = new User();
        Deck deck = new Deck(1L, "Deck 1", "Description 1", new ArrayList<>(), new ArrayList<>(), user);
        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));

        // When
        Deck result = deckService.getDeckById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Deck 1", result.getName());
    }

    @Test
    public void testGetDeckByIdNotFound() {
        // Given
        when(deckRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> deckService.getDeckById(1L));
    }

    @Test
    public void testSaveDeck() {
        // Given
        User user = new User();
        Deck deck = new Deck(1L, "Deck 1", "Description 1", new ArrayList<>(), new ArrayList<>(), user);
        when(deckRepository.save(deck)).thenReturn(deck);

        // When
        Deck result = deckService.saveDeck(deck);

        // Then
        assertNotNull(result);
        assertEquals("Deck 1", result.getName());
    }

    @Test
    public void testAddTagToDeck() {
        // Given
        User user = new User();
        Deck deck = new Deck(1L, "Deck 1", "Description 1", new ArrayList<>(), new ArrayList<>(), user);
        Tag tag = new Tag(1L, "Tag 1", new ArrayList<>());
        TagDto tagDto = new TagDto(1L, "Tag 1", new ArrayList<>());

        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        // When
        deckService.addTagToDeck(1L, tagDto);

        // Then
        assertTrue(deck.getTags().contains(tag));
    }

    @Test
    public void testUpdateTagInDeck() {
        // Given
        Long deckId = 1L;
        Long tagId = 2L;
        Long updatedTagId = 3L;
        User user = new User();
        Deck deck = new Deck(deckId, "Deck 1", "Description 1", new ArrayList<>(), new ArrayList<>(), user);
        Tag existingTag = new Tag(tagId, "Tag 2", new ArrayList<>());
        Tag updatedTag = new Tag(updatedTagId, "Updated Tag", new ArrayList<>());
        deck.getTags().add(existingTag);

        TagDto updatedTagDto = new TagDto(updatedTagId, "Updated Tag", new ArrayList<>());

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(existingTag));
        when(tagRepository.findById(updatedTagId)).thenReturn(Optional.of(updatedTag));

        // When
        deckService.updateTagInDeck(deckId, tagId, updatedTagDto);

        // Then
        assertEquals("Updated Tag", deck.getTags().get(0).getName());
    }
}
