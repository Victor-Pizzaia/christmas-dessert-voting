package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

public record SubscribedDessertDTO(
        DessertId dessertId,
        String name,
        int numberOfVotes,
        int votePosition) {
}
