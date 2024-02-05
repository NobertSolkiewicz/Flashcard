package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.repository.UserRepository;
import com.kodilla.Flashcard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        //Given
        UserDto userDto = new UserDto(1L, "testUser", "TestPassword123!", "test@example.com", null);

        //When
        userService.registerUser(userDto);

        //Then
        User savedUser = userRepository.findByUsername("testUser").orElse(null);
        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
    }
}
