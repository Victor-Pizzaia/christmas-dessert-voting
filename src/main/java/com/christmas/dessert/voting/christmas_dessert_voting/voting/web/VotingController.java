package com.christmas.dessert.voting.christmas_dessert_voting.voting.web;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase.VotingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/voting")
public class VotingController {

    private final VotingUseCase votingUseCase;

    @PostMapping
    public ResponseEntity<Void> createVoting(Authentication authentication, @Validated @RequestBody VotingRequestDTO votingRequestDTO) {
        log.info("Register new voting request received");
        votingUseCase.createVoting(votingRequestDTO, getAuthenticatedId(authentication));
        log.info("{} was registered with successfully", votingRequestDTO.name());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VotingDTO>> getAllVoting() {
        log.info("Get all voting request received");
        List<VotingDTO> voting = votingUseCase.getAllVoting();
        log.info("All voting retrieved successfully");
        return new ResponseEntity<>(voting, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotingDTO> getVotingById(@PathVariable("id") VotingId votingId) {
        log.info("Get voting request received");
        VotingDTO votingDTO = votingUseCase.getVotingById(votingId);
        log.info("Voting details retrieved successfully");
        return new ResponseEntity<>(votingDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateVoting(@PathVariable("id") VotingId id,
                                             @Validated @RequestBody VotingRequestDTO votingRequestDTO) {
        log.info("Update voting request received");
        votingUseCase.updateVoting(id, votingRequestDTO);
        log.info("{} was updated successfully", votingRequestDTO.name());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribeDessertOnVoting(@PathVariable("id") VotingId id,
                                                         @Validated @RequestBody SubscribeDessertRequestDTO subscribeDessertRequestDTO) {
        log.info("Subscribe dessert request received");
        votingUseCase.subscribeDessert(id, subscribeDessertRequestDTO);
        log.info("{} was subscribed successfully", subscribeDessertRequestDTO.name());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private OwnerId getAuthenticatedId(Authentication authentication) {
        String ownerId = authentication.getName();
        return new OwnerId(ownerId);
    }
}
