package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordEncoderServiceImplTest {

    private PasswordEncoderServiceImpl passwordEncoderService;

    @BeforeEach
    void setUp() {
        passwordEncoderService = new PasswordEncoderServiceImpl();
    }

    @Test
    @DisplayName("Hash should return a non-null value")
    void hashShouldReturnNonNullValue() {
        String hashedPassword = passwordEncoderService.hash("plainPassword");
        assertNotNull(hashedPassword);
    }

    @Test
    @DisplayName("Matches should return true for matching passwords")
    void matchesShouldReturnTrueForMatchingPasswords() {
        String plainPassword = "plainPassword";
        String hashedPassword = passwordEncoderService.hash(plainPassword);
        assertTrue(passwordEncoderService.matches(plainPassword, hashedPassword));
    }

    @Test
    @DisplayName("Matches should return false for non-matching passwords")
    void matchesShouldReturnFalseForNonMatchingPasswords() {
        String plainPassword = "plainPassword";
        String hashedPassword = passwordEncoderService.hash(plainPassword);
        assertFalse(passwordEncoderService.matches("wrongPassword", hashedPassword));
    }

    @Test
    @DisplayName("Matches should return false for null hashed password")
    void matchesShouldReturnFalseForNullHashedPassword() {
        assertFalse(passwordEncoderService.matches("plainPassword", null));
    }
}
