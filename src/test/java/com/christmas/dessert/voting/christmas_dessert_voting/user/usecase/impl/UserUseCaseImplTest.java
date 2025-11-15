package com.christmas.dessert.voting.christmas_dessert_voting.user.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.*;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure.UserRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.PasswordEncoderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create user when request is valid")
    void shouldCreateUserWhenRequestIsValid() {
        UserRequestDTO request = new UserRequestDTO(
                "Test",
                "test@example.com",
                "123.456.789-00",
                "plainPassword",
                List.of("Chocolate", "Ice Cream"));

        when(passwordEncoderService.hash("plainPassword")).thenReturn("hashedPassword");

        UserDTO result = userUseCase.createUser(request);

        assertNotNull(result);
        assertEquals("Test", result.name());
        assertEquals(List.of("Chocolate", "Ice Cream"), result.favoriteSweets());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("Test", savedUser.getName());
        assertEquals("12345678900", savedUser.getCpf());
        assertEquals("hashedPassword", savedUser.getPasswordHash());
        assertEquals(request.favoriteSweets(), savedUser.getFavoriteSweets());
    }

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        UserId userId = new UserId();
        User user = new User(userId, "Test", "12345678900", "Test@example.com", "hashed", List.of("Chocolate"));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO result = userUseCase.findUserById(userId);

        assertNotNull(result);
        assertEquals("Test", result.name());
        assertEquals(List.of("Chocolate"), result.favoriteSweets());
    }

    @Test
    @DisplayName("Should return user not found when finding user by id")
    void shouldReturnUserNotFoundWhenFindingUserById() {
        UserId userId = new UserId();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userUseCase.findUserById(userId));
        assertTrue(exception.getMessage().contains(userId.toString()));
    }
}
