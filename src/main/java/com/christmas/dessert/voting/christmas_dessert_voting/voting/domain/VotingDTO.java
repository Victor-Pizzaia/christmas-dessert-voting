package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import java.time.LocalDateTime;
import java.util.Set;

public record VotingDTO(
        String id,
        String name,
        String description,
        int numberOfParticipants,
        boolean isOpenToVote,
        Set<SubscribedDessertDTO> subscribedDesserts,
        LocalDateTime closingDate) {
}
