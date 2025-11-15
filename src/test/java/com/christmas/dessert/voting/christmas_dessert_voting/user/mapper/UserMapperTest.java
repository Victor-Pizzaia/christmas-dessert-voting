package com.christmas.dessert.voting.christmas_dessert_voting.user.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    @DisplayName("Should map User to UserDTO correctly")
    void shouldMapUserToUserDTOCorrectly() {
        User user = new User(
                new UserId(),
                "Test User",
                "12345678900",
                "test@example.com",
                "hashedPassword",
                List.of("Chocolate", "Ice Cream"));

        UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user);

        assertNotNull(userDTO);
        assertEquals("Test User", userDTO.name());
        assertEquals(List.of("Chocolate", "Ice Cream"), userDTO.favoriteSweets());
    }

    @Test
    @DisplayName("Should map User to UserDTO without desserts")
    void shouldMapUserToUserDTOWithoutDesserts() {
        User user = new User(
                new UserId(),
                "Test User",
                "12345678900",
                "test@example.com",
                "hashedPassword",
                null);

        UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user);

        assertNotNull(userDTO);
        assertEquals("Test User", userDTO.name());
        assertNull(userDTO.favoriteSweets());
    }

    @Test
    @DisplayName("Should return null when mapping null User")
    void shouldReturnNullWhenMappingNullUser() {
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(null);
        assertNull(userDTO);
    }
}
