package com.christmas.dessert.voting.christmas_dessert_voting.voting.web;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.usecase.VotingUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingControllerTest {

    @Mock
    private VotingUseCase votingUseCase;

    @InjectMocks
    private VotingController votingController;

    private VotingDTO votingDTO;
    private VotingRequestDTO votingRequestDTO;

    @BeforeEach
    public void setUp() {
        this.votingDTO = new VotingDTO("1", "Christmas Voting", "Choose your favorite dessert",
                0, true, Set.of(), LocalDateTime.now().plusDays(2L));
        this.votingRequestDTO = new VotingRequestDTO("Christmas Voting",
                "Choose your favorite dessert",
                LocalDateTime.now().plusDays(2L));
    }

    @Test
    void shouldCreateVotingSuccessfully() {
        OwnerId ownerId = new OwnerId();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(ownerId.id().toString());


        ResponseEntity<Void> response = votingController.createVoting(authentication, votingRequestDTO);

        verify(votingUseCase).createVoting(eq(votingRequestDTO), eq(new OwnerId(ownerId.id())));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldGetAllVotingSuccessfully() {
        List<VotingDTO> votingList = List.of(votingDTO);
        when(votingUseCase.getAllVoting()).thenReturn(votingList);

        ResponseEntity<List<VotingDTO>> response = votingController.getAllVoting();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votingList, response.getBody());
    }

    @Test
    void shouldGetVotingByIdSuccessfully() {
        VotingId votingId = new VotingId();
        when(votingUseCase.getVotingById(votingId)).thenReturn(votingDTO);

        ResponseEntity<VotingDTO> response = votingController.getVotingById(votingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votingDTO, response.getBody());
    }

    @Test
    void shouldUpdateVotingSuccessfully() {
        VotingId votingId = new VotingId();

        ResponseEntity<Void> response = votingController.updateVoting(votingId, votingRequestDTO);

        verify(votingUseCase).updateVoting(eq(votingId), eq(votingRequestDTO));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldSubscribeDessertOnVotingSuccessfully() {
        VotingId votingId = new VotingId();
        SubscribeDessertRequestDTO subscribeDessertRequestDTO = new SubscribeDessertRequestDTO(new DessertId(), "Tiramisu");

        ResponseEntity<Void> response = votingController.subscribeDessertOnVoting(votingId, subscribeDessertRequestDTO);

        verify(votingUseCase).subscribeDessert(eq(votingId), eq(subscribeDessertRequestDTO));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
