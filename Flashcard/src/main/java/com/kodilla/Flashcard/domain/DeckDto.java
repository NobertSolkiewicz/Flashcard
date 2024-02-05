package com.kodilla.Flashcard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DeckDto {
    private Long deckId;
    private String name;
    private String description;
    private List<Flashcard> flashcards;
    private List<Tag> tags;
    private User user;
}
