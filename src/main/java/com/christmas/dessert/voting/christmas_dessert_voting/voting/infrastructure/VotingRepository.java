package com.christmas.dessert.voting.christmas_dessert_voting.voting.infrastructure;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.Voting;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting, VotingId> {
}
