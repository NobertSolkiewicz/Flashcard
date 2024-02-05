package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.exception.UserRegistrationException;
import com.kodilla.Flashcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) throws UserRegistrationException {
        validateUserData(userDto);

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(hashedPassword);
        user.setConfirmPassword(user.getConfirmPassword());
        user.setRoles(Collections.singleton("ROLE_USER"));

        userRepository.save(user);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public void validateUserData(UserDto userDto) throws UserRegistrationException {
        if (!isPasswordSecure(userDto.getPassword())) {
            throw new UserRegistrationException("Password is not secure enough.");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserRegistrationException("Username is already taken!");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserRegistrationException("Email is already in use!");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new UserRegistrationException("Password do not math!");
        }
    }

    public boolean isPasswordSecure(String password) {
        return password.length() >= 8 &&
                password.length() <= 30 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=].*") &&
                !password.contains("\\s");
    }
}