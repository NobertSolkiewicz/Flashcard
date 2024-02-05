package com.kodilla.Flashcard.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long userId;
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    private String password;
    private String confirmPassword;
    @Email(message = "Invalid email format")
    private String email;

    private String roles;
    private List<Deck> decks;

    public Set<String> getRoles() {

        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
