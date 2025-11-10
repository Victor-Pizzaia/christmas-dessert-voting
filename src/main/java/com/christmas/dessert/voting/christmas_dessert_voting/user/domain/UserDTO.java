package com.christmas.dessert.voting.christmas_dessert_voting.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserDTO(
        String name,
        @JsonProperty("favorite_sweets")
        List<String> favoriteSweets
) {}
