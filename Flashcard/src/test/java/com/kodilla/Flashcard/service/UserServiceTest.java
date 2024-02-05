package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.exception.UserRegistrationException;
import com.kodilla.Flashcard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidator passwordValidator;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder, passwordValidator);
    }

    /*@Test
    void testRegisterUser_SuccessfulRegistration() {
        // Given
        UserDto userDto = new UserDto(1L, "testUser", "SecurePass1@", "test@example.com", new ArrayList<>());
        User user = new User();
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User registeredUser = userService.registerUser(userDto);

        // Then
        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("test@example.com", registeredUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }*/

    @Test
    void testRegisterUser_UsernameAlreadyTaken() {
        // Given
        UserDto userDto = new UserDto(1L, "testUser", "SecurePass1@", "test@example.com", new ArrayList<>());
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        // When and Then
        assertThrows(UserRegistrationException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testRegisterUser_EmailAlreadyInUse() {
        // Given
        UserDto userDto = new UserDto(1L, "testUser", "SecurePass1@", "test@example.com", new ArrayList<>());
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        // When and Then
        assertThrows(UserRegistrationException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testRegisterUser_InvalidEmail() {
        // Given
        UserDto userDto = new UserDto(1L, "testUser", "SecurePass1@", "invalid-email", new ArrayList<>());
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);

        // When and Then
        assertThrows(UserRegistrationException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testRegisterUser_InsecurePassword() {
        // Given
        UserDto userDto = new UserDto(1L, "testUser", "SecurePass1", "test@example.com", new ArrayList<>());
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);

        // When and Then
        assertThrows(UserRegistrationException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        // Given
        User user = new User();
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(user));

        // When
        User loadedUser = userService.loadUserByUsername("testUser");

        // Then
        assertNotNull(loadedUser);
        assertSame(user, loadedUser);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Given
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(java.util.Optional.empty());

        // When and Then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonExistentUser"));
    }

    @Test
    void testIsPasswordSecure_ValidPassword() {
        // Given
        String validPassword = "SecurePass1@";

        // When
        boolean result = userService.isPasswordSecure(validPassword);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsPasswordSecure_ShortPassword() {
        // Given
        String shortPassword = "Short1@"; // Less than 8 characters

        // When
        boolean result = userService.isPasswordSecure(shortPassword);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPasswordSecure_NoLowercaseCharacter() {
        // Given
        String password = "NOLOWER1@"; // No lowercase character

        // When
        boolean result = userService.isPasswordSecure(password);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPasswordSecure_NoUppercaseCharacter() {
        // Given
        String password = "nouppercase1@"; // No uppercase character

        // When
        boolean result = userService.isPasswordSecure(password);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPasswordSecure_NoDigit() {
        // Given
        String password = "NoDigitPass@"; // No digit

        // When
        boolean result = userService.isPasswordSecure(password);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPasswordSecure_NoSpecialCharacter() {
        // Given
        String password = "NoSpecialChar1"; // No special character

        // When
        boolean result = userService.isPasswordSecure(password);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsEmailValid_ValidEmail() {
        // Given
        String validEmail = "test@example.com";

        // When
        boolean result = userService.isEmailValid(validEmail);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsEmailValid_InvalidEmail() {
        // Given
        String invalidEmail = "invalid-email";

        // When
        boolean result = userService.isEmailValid(invalidEmail);

        // Then
        assertFalse(result);
    }
}
