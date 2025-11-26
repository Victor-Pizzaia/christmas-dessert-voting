package com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingRequestDTO;

import java.util.List;

public interface VotingUseCase {
    void createVoting(VotingRequestDTO votingRequestDTO, OwnerId ownerId);
    List<VotingDTO> getAllVoting();
    VotingDTO getVotingById(VotingId votingId);
    void updateVoting(VotingId votingId, VotingRequestDTO votingRequestDTO);
    void subscribeDessert(VotingId votingId, SubscribeDessertRequestDTO subscribeDessertRequestDTO);
}
