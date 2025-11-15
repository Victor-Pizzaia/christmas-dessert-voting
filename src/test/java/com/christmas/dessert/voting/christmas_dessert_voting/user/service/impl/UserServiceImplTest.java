package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return user when identifier exists")
    void shouldReturnUserWhenIdentifierExists() {
        String identifier = "test@example.com";
        User user = new User();

        when(userRepository.findUserByEmailOrCpf(identifier)).thenReturn(Optional.of(user));

        User result = userService.findUserByIdentifier(identifier);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when identifier does not exist")
    void shouldThrowUserNotFoundExceptionWhenIdentifierDoesNotExist() {
        String identifier = "nonexistent@example.com";

        when(userRepository.findUserByEmailOrCpf(identifier)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserByIdentifier(identifier));
    }
}
