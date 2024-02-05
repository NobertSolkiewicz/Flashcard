package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.Deck;
import com.kodilla.Flashcard.domain.DeckDto;
import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.domain.TagDto;
import com.kodilla.Flashcard.mapper.DeckMapper;
import com.kodilla.Flashcard.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;
    private final DeckMapper deckMapper;

    @GetMapping
    public ResponseEntity<List<DeckDto>> getAllDecks() {
        List<Deck> decks = deckService.getAllDecks();
        return ResponseEntity.ok(deckMapper.mapToDeckDtoList(decks));
    }

    @GetMapping(value = "{deckId}")
    public ResponseEntity<DeckDto> getDeck(@PathVariable Long deckId) {
        Deck deck = deckService.getDeckById(deckId);

        if (deck != null) {
            return ResponseEntity.ok(deckMapper.mapToDeckDto(deck));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<DeckDto> updateDeck(@RequestBody DeckDto deckDto) {
        Deck deck = deckMapper.mapToDeck(deckDto);
        Deck savedDeck = deckService.saveDeck(deck);
        return ResponseEntity.ok(deckMapper.mapToDeckDto(savedDeck));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createDeck(@RequestBody DeckDto deckDto) {
        if (isValidDeckData(deckDto)) {
            Deck deck = deckMapper.mapToDeck(deckDto);
            deckService.saveDeck(deck);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{deckId}/tags")
    public ResponseEntity<Void> addTagToDeck(@PathVariable Long deckId, @RequestBody TagDto tagDto) {
        deckService.addTagToDeck(deckId, tagDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{deckId}/tags/{tagId}")
    public ResponseEntity<Void> updateTagInDeck (
            @PathVariable Long deckId,
            @PathVariable Long tagId,
            @RequestBody TagDto tagDto) {
        deckService.updateTagInDeck(deckId, tagId, tagDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{deckId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromDeck (
            @PathVariable Long deckId,
            @PathVariable Long tagId) {
        deckService.removeTagFromDeck(deckId, tagId);
        return ResponseEntity.ok().build();
    }

    private boolean isValidDeckData(DeckDto deckDto) {
        if (deckDto == null) {
            return false;
        }

        String deckName = deckDto.getName();
        List<Tag> deckTag = deckDto.getTags();

        return deckName != null && !deckName.trim().isEmpty() && deckTag != null && !deckTag.isEmpty();
    }
}
