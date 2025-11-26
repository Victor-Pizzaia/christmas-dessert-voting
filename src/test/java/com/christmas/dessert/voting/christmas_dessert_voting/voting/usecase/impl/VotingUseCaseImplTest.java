package com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.Voting;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.exception.VotingNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.infrastructure.VotingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingUseCaseImplTest {

    @Mock
    private VotingRepository votingRepository;

    @InjectMocks
    private VotingUseCaseImpl votingUseCase;

    private VotingRequestDTO votingRequestDTO;
    private OwnerId ownerId;
    private VotingId votingId;
    private Voting voting;

    @BeforeEach
    void setUp() {
        ownerId = new OwnerId();
        votingId = new VotingId();
        votingRequestDTO = new VotingRequestDTO("Christmas Voting", "Choose your favorite dessert",
                LocalDateTime.now().plusDays(7));
        voting = new Voting(votingId, ownerId, "Christmas Voting", "Choose your favorite dessert",
                LocalDateTime.now().plusDays(7));
    }

    @Test
    void testCreateVoting() {
        votingUseCase.createVoting(votingRequestDTO, ownerId);

        verify(votingRepository, times(1)).save(any(Voting.class));
    }

    @Test
    void testGetAllVoting() {
        List<Voting> votings = List.of(voting);
        when(votingRepository.findAll()).thenReturn(votings);

        List<VotingDTO> result = votingUseCase.getAllVoting();

        assertNotNull(result);
        verify(votingRepository, times(1)).findAll();
    }

    @Test
    void testGetVotingById_Success() {
        when(votingRepository.findById(votingId)).thenReturn(Optional.of(voting));

        VotingDTO result = votingUseCase.getVotingById(votingId);

        assertNotNull(result);
        verify(votingRepository, times(1)).findById(votingId);
    }

    @Test
    void testGetVotingById_NotFound() {
        when(votingRepository.findById(votingId)).thenReturn(Optional.empty());

        assertThrows(VotingNotFoundException.class, () -> votingUseCase.getVotingById(votingId));
        verify(votingRepository, times(1)).findById(votingId);
    }

    @Test
    void testUpdateVoting_Success() {
        when(votingRepository.findById(votingId)).thenReturn(Optional.of(voting));

        votingUseCase.updateVoting(votingId, votingRequestDTO);

        verify(votingRepository, times(1)).save(voting);
    }

    @Test
    void testUpdateVoting_NotFound() {
        when(votingRepository.findById(votingId)).thenReturn(Optional.empty());

        assertThrows(VotingNotFoundException.class, () -> votingUseCase.updateVoting(votingId, votingRequestDTO));
    }

    @Test
    void testSubscribeDessert_Success() {
        SubscribeDessertRequestDTO subscribeDessertRequestDTO = new SubscribeDessertRequestDTO(new DessertId(), "Tiramisu");
        when(votingRepository.findById(votingId)).thenReturn(Optional.of(voting));

        votingUseCase.subscribeDessert(votingId, subscribeDessertRequestDTO);

        verify(votingRepository, times(1)).save(voting);
    }

    @Test
    void testSubscribeDessert_NotFound() {
        SubscribeDessertRequestDTO subscribeDessertRequestDTO = new SubscribeDessertRequestDTO(new DessertId(), "Tiramisu");
        when(votingRepository.findById(votingId)).thenReturn(Optional.empty());

        assertThrows(VotingNotFoundException.class,
                () -> votingUseCase.subscribeDessert(votingId, subscribeDessertRequestDTO));
    }
}
