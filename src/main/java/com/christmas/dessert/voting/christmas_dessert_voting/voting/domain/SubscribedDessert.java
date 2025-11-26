package com.christmas.dessert.voting.christmas_dessert_voting.voting.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUBSCRIBED_DESSERTS")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubscribedDessert {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private DessertId dessertId;
    @Column(nullable = false)
    private String name;
    @Column(name = "number_of_votes")
    private int numberOfVotes;
    @Column(name = "vote_position")
    private int votePosition;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voting_id", nullable = false)
    private Voting voting;

    public SubscribedDessert(DessertId dessertId, String name, Voting voting) {
        this.dessertId = dessertId;
        this.name = name;
        this.numberOfVotes = 0;
        this.votePosition = 0;
        this.voting = voting;
    }
}
