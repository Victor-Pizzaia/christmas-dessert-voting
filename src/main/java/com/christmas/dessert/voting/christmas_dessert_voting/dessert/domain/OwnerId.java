package com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record OwnerId(UUID id) {
    public OwnerId {
        Assert.notNull(id, "Id must not be null");
    }

    public OwnerId() {
        this(UUID.randomUUID());
    }

    public OwnerId(String id) {
        this(UUID.fromString(id));
    }
}
