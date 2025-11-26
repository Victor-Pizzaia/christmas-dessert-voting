package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOTING")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Voting {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private VotingId id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private OwnerId ownerId;
    @Setter
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    @Setter
    private String description;
    @Setter
    @Column(name = "number_of_participants")
    private int numberOfParticipants;
    @Setter
    @Column(name = "is_open_to_vote")
    private boolean isOpenToVote;
    @OneToMany(
            mappedBy = "voting",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<SubscribedDessert> subscribedDesserts = new HashSet<>();
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Setter
    @Column(name = "closing_date")
    private LocalDateTime closingDate;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Voting(VotingId id, OwnerId ownerId, String name, String description, LocalDateTime closingDate) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.closingDate = closingDate;
        this.isOpenToVote = false;
        this.numberOfParticipants = 0;
        this.subscribedDesserts = new HashSet<>();
        this.id = id;
    }

    public void subscribeDessert(SubscribeDessertRequestDTO subscribeDessertRequestDTO) {
        if (this.subscribedDesserts == null) {
            this.subscribedDesserts = new HashSet<>();
        }
        this.subscribedDesserts.add(new SubscribedDessert(
                subscribeDessertRequestDTO.dessertId(),
                subscribeDessertRequestDTO.name(),
                this));
    }
}
