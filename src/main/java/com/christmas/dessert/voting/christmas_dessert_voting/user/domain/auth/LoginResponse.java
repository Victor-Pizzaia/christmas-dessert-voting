package com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth;

public record LoginResponse(String token, long expiresIn) {
}
