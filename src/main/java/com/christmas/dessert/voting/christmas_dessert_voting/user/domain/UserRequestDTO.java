package com.christmas.dessert.voting.christmas_dessert_voting.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record UserRequestDTO(
        @NotNull
        @NotBlank
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "Name must contain only letters and spaces")
        String name,
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        @CPF
        String cpf,
        @NotNull
        @NotBlank
        String plainPassword,
        @JsonProperty("favorite_sweets")
        List<String> favoriteSweets
) {}
