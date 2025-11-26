package com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "DESSERTS")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dessert {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private DessertId id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private OwnerId ownerId;
    @Setter
    @Column(nullable = false, unique = true)
    private String name;
    @Setter
    @Column
    private String description;
    @Column(name = "is_subscribed")
    private boolean isSubscribed;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Dessert(DessertId id, OwnerId ownerId, String name, String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.isSubscribed = false;
    }
}
