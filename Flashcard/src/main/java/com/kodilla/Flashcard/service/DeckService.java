package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.domain.TagDto;
import com.kodilla.Flashcard.repository.DeckRepository;
import com.kodilla.Flashcard.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;
    private final TagRepository tagRepository;

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    public Deck getDeckById(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Deck not found"));
    }

    public Deck saveDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public void deleteDeck(Long id) {
        deckRepository.deleteById(id);
    }

    public void addTagToDeck(Long deckId, TagDto tagDto) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new NoSuchElementException("Deck not found"));

        Tag tag = tagRepository.findById(tagDto.getTagId())
                .orElseThrow(() -> new NoSuchElementException("Tag not found"));
        deck.getTags().add(tag);
        deckRepository.save(deck);
    }

    public void updateTagInDeck(Long deckId, Long tagId, TagDto tagDto) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new NoSuchElementException("Deck not found"));

        List<Tag> tags = deck.getTags();
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getTagId().equals(tagId)) {
                Tag updateTag = tagRepository.findById(tagDto.getTagId())
                        .orElseThrow(() -> new NoSuchElementException("Tag not found"));
                tags.set(i, updateTag);
                deckRepository.save(deck);
                return;
            }
        }
    }

    public void removeTagFromDeck(Long deckId, Long tagId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new NoSuchElementException("Deck not found"));

        List<Tag> tags = deck.getTags();
        tags.removeIf(tag -> tag.getTagId().equals(tagId));

        deckRepository.save(deck);
    }
}
