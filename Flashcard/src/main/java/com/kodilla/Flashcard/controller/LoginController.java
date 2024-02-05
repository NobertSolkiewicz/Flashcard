package com.kodilla.Flashcard.controller;

import com.kodilla.Flashcard.security.JwtTokenProvider;
import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequestMapping("/auth/login")
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        try {
            User user = userService.loadUserByUsername(userDto.getUsername());
            if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), userDto.getPassword())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtTokenProvider.generateToken(authentication);

                Cookie cookie = new Cookie("JSESSIONID", token);
                cookie.setPath("/");
                response.addCookie(cookie);

                System.out.println("Logowanie udane, zwracanie tokena.");
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Location", "/main")
                        .body(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
        } catch (BadCredentialsException e) {
            System.out.println("Błędne dane. UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
            securityContextLogoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("Successful logout");
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            System.out.println("Error occured while logout" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}