package com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record DessertId(UUID id) {
    public DessertId {
        Assert.notNull(id, "Id must not be null");
    }

    public DessertId() {
        this(UUID.randomUUID());
    }

    public DessertId(String id) {
        this(UUID.fromString(id));
    }
}
