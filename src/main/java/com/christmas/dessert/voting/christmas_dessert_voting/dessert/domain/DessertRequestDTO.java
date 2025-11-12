package com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DessertRequestDTO(@NotNull @NotBlank String name, String description) {
}
