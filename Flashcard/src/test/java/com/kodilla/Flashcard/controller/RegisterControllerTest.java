/*
package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.repository.UserRepository;
import com.kodilla.Flashcard.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class RegisterControllerTest {
    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidationService passwordValidationService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterValidUser() {
        //Given
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordValidationService.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        //When
        //ResponseEntity<?> response = registerController.(userDto, bindingResult);

        //Then
        //assertEquals(ResponseEntity.ok("User registered successfully!"), response);
    }

    @Test
    void testRegisterInvalidInputData() {
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = registerController.register(userDto, bindingResult);

        assertEquals(ResponseEntity.badRequest().body("Invalid input data"), response);
    }

    @Test
    void testRegisterInvalidPassword() {
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordValidationService.isValidPassword(anyString())).thenReturn(false);

        ResponseEntity<?> response = registerController.register(userDto, bindingResult);

        assertEquals(ResponseEntity.badRequest().body("Invalid password"), response);
    }

    @Test
    void testRegisterUsernameAlreadyTaken() {
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordValidationService.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        ResponseEntity<?> response = registerController.register(userDto, bindingResult);

        assertEquals(ResponseEntity.badRequest().body("Username is already taken!"), response);
    }

    @Test
    void testRegisterEmailAlreadyInUse() {
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordValidationService.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<?> response = registerController.register(userDto, bindingResult);

        assertEquals(ResponseEntity.badRequest().body("Email is already in use!"), response);
    }

    @Test
    void testRegisterInternalServerError() {
        UserDto userDto = new UserDto(1L, "testUser", "testP@assword1", "test@email.com", new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordValidationService.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(userRepository.save((UserDto) any())).thenThrow(new RuntimeException("Some error"));

        ResponseEntity<?> response = registerController.register(userDto, bindingResult);

        assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!"), response);
    }
}
*/
