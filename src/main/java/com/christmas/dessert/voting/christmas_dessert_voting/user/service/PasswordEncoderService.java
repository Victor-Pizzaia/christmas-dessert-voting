package com.christmas.dessert.voting.christmas_dessert_voting.user.service;

public interface PasswordEncoderService {
    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);
}
