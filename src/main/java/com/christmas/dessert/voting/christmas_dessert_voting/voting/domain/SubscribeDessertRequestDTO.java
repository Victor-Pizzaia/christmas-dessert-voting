package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscribeDessertRequestDTO(@NotNull DessertId dessertId, @NotNull @NotBlank String name) {
}
