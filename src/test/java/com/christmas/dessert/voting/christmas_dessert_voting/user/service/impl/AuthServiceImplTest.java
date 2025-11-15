package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.christmas.dessert.voting.christmas_dessert_voting.infra.JwtProvider;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginRequest;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginResponse;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserPasswordWrongException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.PasswordEncoderService;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthServiceImplTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return LoginResponse when credentials are valid")
    void shouldReturnLoginResponseWhenCredentialsAreValid() {
        LoginRequest loginRequest = new LoginRequest("identifier", "password");
        User user = new User(new UserId(), "Jhon Due", "33344455566", "jhon@gmail.com","hashedPassword", null);

        when(userService.findUserByIdentifier("identifier")).thenReturn(user);
        when(passwordEncoderService.matches("password", "hashedPassword")).thenReturn(true);
        when(jwtProvider.generateToken(user.getId().id().toString(), "identifier")).thenReturn("jwtToken");
        when(jwtProvider.getExpiresIn()).thenReturn(3600L);

        LoginResponse response = authService.login(loginRequest);

        assertEquals("jwtToken", response.token());
        assertEquals(3600L, response.expiresIn());
    }

    @Test
    @DisplayName("Should throw UserPasswordWrongException when password is incorrect")
    void shouldThrowUserPasswordWrongExceptionWhenPasswordIsIncorrect() {
        LoginRequest loginRequest = new LoginRequest("identifier", "wrongPassword");
        User user = new User();

        when(userService.findUserByIdentifier("identifier")).thenReturn(user);
        when(passwordEncoderService.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(UserPasswordWrongException.class, () -> authService.login(loginRequest));
    }
}
