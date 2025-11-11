package com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull @NotBlank String identifier, @NotNull @NotBlank String password) {
}
