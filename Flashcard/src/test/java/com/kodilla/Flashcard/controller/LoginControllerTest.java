package com.kodilla.Flashcard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.Flashcard.security.JwtTokenProvider;
import com.kodilla.Flashcard.domain.User;
import com.kodilla.Flashcard.domain.UserDto;
import com.kodilla.Flashcard.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testLoginEndpointWithValidCredentials() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L, "root", "M@ciek08", "example@test.com", null);
        User mockUser = new User(1L, "root", "M@ciek08", "example@test.com", null);

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                mockUser.getUsername(),
                mockUser.getPassword()
        );

        String mockToken = "mockedToken";

        when(userService.loadUserByUsername(userDto.getUsername())).thenReturn(mockUser);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtTokenProvider.generateToken(mockAuthentication)).thenReturn(mockToken);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/main"));
    }

    @Test
    void testLoginEndpointWithInvalidCredentials() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "root", "M@ciek08", "example@test.com", null);

        when(userService.loadUserByUsername(userDto.getUsername())).thenReturn(null);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testLoginEndpointWithBadCredentialsException() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "root", "test", "example@test.com", new ArrayList<>());
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username("root").password("test").build();

        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"root\",\"password\":\"test\"}");

       mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andReturn();

        //Then
    }
}
