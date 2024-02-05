package com.kodilla.Flashcard.repository;

import com.kodilla.Flashcard.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        //Given
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setEmail("test@example.com");
        user.setPassword("Password.!23");

        userRepository.save(user);

        //When
        Optional<User> foundUserOptional = userRepository.findByUsername(username);

        //Then
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindByEmail() {
        //Given
        String email = "test@example.com";
        User user = new User();
        user.setUsername("testUser");
        user.setEmail(email);
        user.setPassword("Password.!23");

        userRepository.save(user);

        //When
        Optional<User> foundUserOptional = userRepository.findByEmail(email);

        //Then
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(email, foundUser.getEmail());
    }
}
