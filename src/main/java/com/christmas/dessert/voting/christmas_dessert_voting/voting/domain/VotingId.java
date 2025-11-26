package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record VotingId(UUID id) {
    public VotingId {
        Assert.notNull(id, "Id must not be null");
    }

    public VotingId() {
        this(UUID.randomUUID());
    }

    public VotingId(String id) {
        this(UUID.fromString(id));
    }
}
