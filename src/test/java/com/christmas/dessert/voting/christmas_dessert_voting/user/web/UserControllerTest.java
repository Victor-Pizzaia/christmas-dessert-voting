package com.christmas.dessert.voting.christmas_dessert_voting.user.web;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginRequest;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginResponse;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.AuthService;
import com.christmas.dessert.voting.christmas_dessert_voting.user.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create user and return CREATED status")
    void shouldCreateUserAndReturnCreatedStatus() {
        UserRequestDTO request = new UserRequestDTO(
                "Test",
                "test@example.com",
                "123.456.789-00",
                "plainPassword",
                List.of("Chocolate", "Ice Cream"));

        UserDTO responseDto = new UserDTO("Test", List.of("Chocolate", "Ice Cream"));
        when(userUseCase.createUser(request)).thenReturn(responseDto);

        ResponseEntity<UserDTO> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().name());
        assertEquals(List.of("Chocolate", "Ice Cream"), response.getBody().favoriteSweets());
        verify(userUseCase, times(1)).createUser(request);
    }

    @Test
    @DisplayName("Should get user info and return OK status")
    void shouldGetUserInfoAndReturnOkStatus() {
        UserId userId = new UserId();
        UserDTO userDTO = new UserDTO("Test", List.of("Chocolate"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userId.id().toString());
        when(userUseCase.findUserById(userId)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserInfo(authentication);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().name());
        assertEquals(List.of("Chocolate"), response.getBody().favoriteSweets());
        verify(userUseCase, times(1)).findUserById(userId);
    }

    @Test
    @DisplayName("Should login user and return OK status")
    void shouldLoginUserAndReturnOkStatus() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        LoginResponse loginResponse = new LoginResponse("token123", 3600L);

        when(authService.login(loginRequest)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> response = userController.login(loginRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token123", response.getBody().token());
        verify(authService, times(1)).login(loginRequest);
    }
}
