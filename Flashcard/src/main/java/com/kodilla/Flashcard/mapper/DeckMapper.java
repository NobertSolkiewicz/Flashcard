package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.DeckDto;
import com.kodilla.Flashcard.domain.TagDto;
import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.repository.FlashcardRepository;
import com.kodilla.Flashcard.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckMapper {
    private FlashcardRepository flashcardRepository;
    private TagRepository tagRepository;
    private TagMapper tagMapper;

    public Deck mapToDeck(final DeckDto deckDto) {
        Deck deck = new Deck();
        deck.setDeckId(deckDto.getDeckId());
        deck.setName(deckDto.getName());
        deck.setDescription(deckDto.getDescription());

        User user = deckDto.getUser();
        deck.setUser(user);
        return deck;
    }

    public DeckDto mapToDeckDto(final Deck deck) {
        List<TagDto> tagDtos = deck.getTags().stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
        return new DeckDto(
                deck.getDeckId(),
                deck.getName(),
                deck.getDescription(),
                deck.getFlashcards(),
                deck.getTags(),
                deck.getUser()
        );
    }

    public List<DeckDto> mapToDeckDtoList(final List<Deck> deckList) {
        return deckList.stream()
                .map(this::mapToDeckDto)
                .collect(Collectors.toList());
    }
}
