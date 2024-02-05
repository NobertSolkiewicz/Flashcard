package com.kodilla.Flashcard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagDto {
    private Long tagId;
    private String name;
    private List<Flashcard> flashcards;
}
