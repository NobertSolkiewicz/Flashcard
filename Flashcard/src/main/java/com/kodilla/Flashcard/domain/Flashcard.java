package com.kodilla.Flashcard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flashcard_id;

    @Column
    private String question;

    @Column
    private String answer;

    @ManyToOne
    @JoinColumn(name = "deckId")
    private Deck deck;

    @ManyToMany
    @JoinTable(
            name = "flashcard_tags",
            joinColumns = @JoinColumn(name = "flashcardId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tags = new ArrayList<>();
}
