package com.kodilla.Flashcard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FlashcardDto {
    private Long flashCardId;
    private String question;
    private String answer;
    private Deck deck;
    private List<Long> tagsId;
}
