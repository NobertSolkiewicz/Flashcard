package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.*;
import com.kodilla.Flashcard.repository.DeckRepository;
import com.kodilla.Flashcard.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class FlashcardMapperTest {
    @Mock
    private DeckRepository deckRepository;

    @Mock
    private TagRepository tagRepository;

    private FlashcardMapper flashcardMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flashcardMapper = new FlashcardMapper(deckRepository, tagRepository);
    }

    @Test
    public void testMapToFlashcard() {
        // Given
        Deck deck = new Deck();
        deck.setDeckId(1L);

        FlashcardDto flashcardDto = new FlashcardDto(1L, "Question 1", "Answer 1", deck, Collections.singletonList(1L));

        List<Tag> tags = Collections.singletonList(new Tag(1L, "Test Tag", Collections.emptyList()));

        when(deckRepository.findById(1L)).thenReturn(java.util.Optional.of(deck));
        when(tagRepository.findAllById(Collections.singletonList(1L))).thenReturn(tags);

        //Wehn
        Flashcard flashcard = flashcardMapper.mapToFlashcard(flashcardDto);

        //Then
        assertEquals(1L, flashcard.getFlashcard_id());
        assertEquals("Test Question", flashcard.getQuestion());
        assertEquals("Test Answer", flashcard.getAnswer());
        assertEquals(1L, flashcard.getDeck().getDeckId());
        assertEquals(1, flashcard.getTags().size());
        assertEquals("Test Tag", flashcard.getTags().get(0).getName());
    }

    @Test
    public void testMapToFlashcardDto() {
        //Given
        Deck deck = new Deck();
        deck.setDeckId(1L);

        List<Tag> tags = Collections.singletonList(new Tag(1L, "Test Tag", Collections.emptyList()));

        Flashcard flashcard = new Flashcard();
        flashcard.setFlashcard_id(1L);
        flashcard.setQuestion("Test Question");
        flashcard.setAnswer("Test Answer");
        flashcard.setDeck(deck);
        flashcard.setTags(tags);

        //When
        FlashcardDto flashcardDto = flashcardMapper.mapToFlashcardDto(flashcard);

        //Then
        assertEquals(1L, flashcardDto.getFlashCardId());
        assertEquals("Test Question", flashcardDto.getQuestion());
        assertEquals("Test Answer", flashcardDto.getAnswer());
        assertEquals(1L, flashcardDto.getDeck().getDeckId());
        assertEquals(1, flashcardDto.getTagsId().size());
        assertEquals(1L, flashcardDto.getTagsId().get(0));
    }

    @Test
    public void testMapToFlashcardDtoList() {
        // Given
        User user1 = new User();
        Deck deck1 = new Deck(1L, "Test Name 1", "Test Description 1", new ArrayList<>(), new ArrayList<>(), user1);
        Deck deck2 = new Deck(2L, "Test Name2 ", "Test Description 2", new ArrayList<>(), new ArrayList<>(), user1);

        Tag tag1 = new Tag(1L, "Tag 1", new ArrayList<>());
        Tag tag2 = new Tag(2L, "Tag 2", new ArrayList<>());

        Flashcard flashcard1 = new Flashcard(1L, "Question 1", "Answer 1", deck1, Arrays.asList(tag1));
        Flashcard flashcard2 = new Flashcard(2L, "Question 2", "Answer 2", deck2, Arrays.asList(tag2));
        List<Flashcard> flashcards = Arrays.asList(flashcard1, flashcard2);

        // When
        List<FlashcardDto> flashcardDtos = flashcardMapper.mapToFlashcardDtoList(flashcards);

        // Then
        assertEquals(2, flashcardDtos.size());
        assertEquals(1L, flashcardDtos.get(0).getFlashCardId());
        assertEquals("Question 1", flashcardDtos.get(0).getQuestion());
        assertEquals("Answer 1", flashcardDtos.get(0).getAnswer());

        assertEquals(2L, flashcardDtos.get(1).getFlashCardId());
        assertEquals("Question 2", flashcardDtos.get(1).getQuestion());
        assertEquals("Answer 2", flashcardDtos.get(1).getAnswer());
    }

}
