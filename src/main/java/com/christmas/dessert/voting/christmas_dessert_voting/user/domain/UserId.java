package com.christmas.dessert.voting.christmas_dessert_voting.user.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {
    public UserId {
        Assert.notNull(id, "Id must not be null");
    }

    public UserId() {
        this(UUID.randomUUID());
    }

    public UserId(String id) {
        this(UUID.fromString(id));
    }
}
