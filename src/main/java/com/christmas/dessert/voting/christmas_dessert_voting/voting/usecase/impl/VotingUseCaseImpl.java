package com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.Voting;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.exception.VotingNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.infrastructure.VotingRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.mapper.VotingMapper;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase.VotingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingUseCaseImpl implements VotingUseCase {

    private final VotingRepository votingRepository;
    private static final String VOTING_NOT_FOUND_MESSAGE = "Voting not found with id: ";

    @Override
    @Transactional
    public void createVoting(VotingRequestDTO votingRequestDTO, OwnerId ownerId) {
        Voting newVoting = new Voting(
                new VotingId(),
                ownerId,
                votingRequestDTO.name(),
                votingRequestDTO.description(),
                votingRequestDTO.closingDate());

        votingRepository.save(newVoting);
        log.debug("Success on creating a new voting: {}", newVoting.getId());
    }

    @Override
    public List<VotingDTO> getAllVoting() {
        List<Voting> votingList = votingRepository.findAll();
        return VotingMapper.INSTANCE.listVotingToListVotingDTOs(votingList);
    }

    @Override
    public VotingDTO getVotingById(VotingId votingId) {
        Voting voting = votingRepository.findById(votingId)
                .orElseThrow(() -> new VotingNotFoundException(VOTING_NOT_FOUND_MESSAGE + votingId));
        return VotingMapper.INSTANCE.votingToVotingDTO(voting);
    }

    @Override
    @Transactional
    public void updateVoting(VotingId votingId, VotingRequestDTO votingRequestDTO) {
        Voting voting = votingRepository.findById(votingId)
                .orElseThrow(() -> new VotingNotFoundException(VOTING_NOT_FOUND_MESSAGE + votingId));
        voting.setName(votingRequestDTO.name());
        voting.setDescription(votingRequestDTO.description());
        voting.setClosingDate(votingRequestDTO.closingDate());

        votingRepository.save(voting);
        log.debug("Success on updating voting: {}", votingId);
    }

    @Override
    @Transactional
    public void subscribeDessert(VotingId votingId, SubscribeDessertRequestDTO subscribeDessertRequestDTO) {
        Voting voting = votingRepository.findById(votingId)
                .orElseThrow(() -> new VotingNotFoundException(VOTING_NOT_FOUND_MESSAGE + votingId));

        voting.subscribeDessert(subscribeDessertRequestDTO);
        voting.setNumberOfParticipants(voting.getNumberOfParticipants() + 1);
        votingRepository.save(voting);
        log.debug("Success on subscribing dessert: {} to voting: {}", subscribeDessertRequestDTO.name(), votingId);
    }
}
