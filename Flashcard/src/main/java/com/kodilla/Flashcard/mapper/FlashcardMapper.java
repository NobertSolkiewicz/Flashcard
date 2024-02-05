package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.Flashcard;
import com.kodilla.Flashcard.domain.FlashcardDto;
import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.repository.DeckRepository;
import com.kodilla.Flashcard.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlashcardMapper {
    private DeckRepository deckRepository;
    private TagRepository tagRepository;

    @Autowired

    public FlashcardMapper(DeckRepository deckRepository, TagRepository tagRepository) {
        this.deckRepository = deckRepository;
        this.tagRepository = tagRepository;
    }

    public Flashcard mapToFlashcard(final FlashcardDto flashcardDto) {
        Flashcard flashcard = new Flashcard();
        flashcard.setFlashcard_id(flashcardDto.getFlashCardId());
        flashcard.setQuestion(flashcardDto.getQuestion());
        flashcard.setAnswer(flashcardDto.getAnswer());

        Deck deck = new Deck();
        deck.setDeckId(flashcardDto.getDeck().getDeckId());
        flashcard.setDeck(deck);

        List<Tag> tags = tagRepository.findAllById(flashcardDto.getTagsId());
        flashcard.setTags(tags);

        return flashcard;
    }

    public FlashcardDto mapToFlashcardDto(final Flashcard flashcard) {
        return new FlashcardDto(
                flashcard.getFlashcard_id(),
                flashcard.getQuestion(),
                flashcard.getAnswer(),
                flashcard.getDeck(),
                flashcard.getTags().stream()
                        .map(Tag::getTagId).collect(Collectors.toList())
        );
    }

    public List<FlashcardDto> mapToFlashcardDtoList(final List<Flashcard> flashcards) {
        return flashcards.stream()
                .map(this::mapToFlashcardDto)
                .collect(Collectors.toList());
    }
}
