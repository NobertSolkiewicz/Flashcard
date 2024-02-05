package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.exception.UserRegistrationException;
import com.kodilla.Flashcard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, HttpServletResponse response) {
        System.out.println("Received user data: " + userDto);
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        System.out.println("Received registration request for user: " + userDto.getUsername());

        try {
            userService.registerUser(userDto);

            response.setHeader(HttpHeaders.LOCATION, "/login");

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
        }
    }
}
