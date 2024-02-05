package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DeckMapperTest {
    @InjectMocks
    private DeckMapper deckMapper;

    @Mock
    private TagMapper tagMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapToDeck() {
        //Given
        User user = new User();
        DeckDto deckDto = new DeckDto(1L, "Test Deck", "Test Description", Collections.emptyList(), Collections.emptyList(), user);

        Deck deck = deckMapper.mapToDeck(deckDto);

        List<Tag> expectedTag = new ArrayList<>();
        List<Flashcard> flashcards = new ArrayList<>();
        expectedTag.add(new Tag(1L, "Test tag 1", flashcards));
        expectedTag.add(new Tag(2L, "Test tag 2", flashcards));
        deck.setTags(expectedTag);

        //When & Then
        assertEquals(1L, deck.getDeckId());
        assertEquals("Test Deck", deck.getName());
        assertEquals("Test Description", deck.getDescription());
        assertEquals(expectedTag, deck.getTags());
        assertEquals(user, deck.getUser());
    }

    @Test
    public void testMapToDeckDto() {
        //Given
        List<Tag> tags = new ArrayList<>();
        List<Flashcard> flashcards = new ArrayList<>();
        tags.add(new Tag(1L, "Test tag 1", flashcards));
        tags.add(new Tag(2L, "Test tag 2", flashcards));

        Deck deck = new Deck();
        deck.setDeckId(1L);
        deck.setName("Test Deck");
        deck.setDescription("Test Description");
        deck.setTags(tags);

        //When
        when(tagMapper.mapToTagDto(tags.get(0))).thenReturn(new TagDto(1L, "Test tag 1", flashcards));
        when(tagMapper.mapToTagDto(tags.get(1))).thenReturn(new TagDto(2L, "Test tag 2", flashcards));

        DeckDto deckDto = deckMapper.mapToDeckDto(deck);

        //Thern
        assertEquals(1L, deckDto.getDeckId());
        assertEquals("Test Deck", deckDto.getName());
        assertEquals("Test Description", deckDto.getDescription());
        assertEquals(2, deckDto.getTags().size());
    }

    @Test
    public void testMapToDeckDtoList() {
        List<Deck> deckList = new ArrayList<>();

        deckList.add(new Deck(1L, "1 Test Deck", "Test Description 1", Collections.emptyList() ,Collections.emptyList(), new User()));
        deckList.add(new Deck(2L, "2 Test Deck", "Test Description 2", Collections.emptyList() ,Collections.emptyList(), new User()));

        List<Flashcard> flashcards = new ArrayList<>();

        when(tagMapper.mapToTagDto(new Tag(1L, "Test tag 1", flashcards))).thenReturn(new TagDto(1L, "Test tag 1", flashcards));
        when(tagMapper.mapToTagDto(new Tag(2L, "Test tag 2", flashcards))).thenReturn(new TagDto(2L, "Test tag 2", flashcards));

        List<DeckDto> deckDtoList = deckMapper.mapToDeckDtoList(deckList);

        assertEquals(2, deckDtoList.size());
        assertEquals(1L, deckDtoList.get(0).getDeckId());
        assertEquals(2L, deckDtoList.get(1).getDeckId());
    }
}
