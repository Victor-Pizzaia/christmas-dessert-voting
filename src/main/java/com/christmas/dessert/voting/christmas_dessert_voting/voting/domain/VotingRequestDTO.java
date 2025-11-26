package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VotingRequestDTO(
        @NotNull
        @NotBlank
        String name,
        String description,
        @Future
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime closingDate) {
}
