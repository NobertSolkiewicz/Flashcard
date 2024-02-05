package com.kodilla.Flashcard.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/main-menu")
public class MainMenuController {

    @GetMapping
    public List<String> getMenu() {
        return Arrays.asList("Create Flashcard", "My Decks", "My Account", "Log Out");
    }
}
