package com.christmas.dessert.voting.christmas_dessert_voting.voting.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.SubscribeDessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.Voting;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class VotingMapperTest {

    private final VotingMapper votingMapper = VotingMapper.INSTANCE;

    @Test
    void shouldMapVotingToVotingDTO() {
        Voting voting = new Voting(new VotingId(),
                new OwnerId(),
                "Christmas Dessert Voting",
                "Choose your favorite dessert",
                LocalDateTime.now().plusDays(2L));
        voting.subscribeDessert(new SubscribeDessertRequestDTO(new DessertId(), "Tiramisu"));

        VotingDTO votingDTO = votingMapper.votingToVotingDTO(voting);

        assertNotNull(votingDTO);
        assertEquals(voting.getId(), new VotingId(votingDTO.id()));
        assertEquals(voting.getName(), votingDTO.name());
        assertEquals(voting.getDescription(), votingDTO.description());
        assertEquals(voting.getNumberOfParticipants(), votingDTO.numberOfParticipants());
        assertEquals(voting.getClosingDate(), votingDTO.closingDate());
        assertEquals(voting.isOpenToVote(), votingDTO.isOpenToVote());
        assertEquals(voting.getSubscribedDesserts().size(), votingDTO.subscribedDesserts().size());
    }

    @Test
    void shouldMapEmptyVotingListToEmptyVotingDTOList() {
        List<Voting> votingList = Collections.emptyList();

        List<VotingDTO> votingDTOList = votingMapper.listVotingToListVotingDTOs(votingList);

        assertNotNull(votingDTOList);
        assertTrue(votingDTOList.isEmpty());
    }

    @Test
    void shouldMapVotingListToVotingDTOList() {
        Voting voting1 = new Voting(new VotingId(),
                new OwnerId(),
                "Christmas Dessert Voting",
                "Choose your favorite dessert",
                LocalDateTime.now().plusDays(2L));

        Voting voting2 = new Voting(new VotingId(),
                new OwnerId(),
                "Christmas Dessert Voting",
                "Choose your favorite dessert",
                LocalDateTime.now().plusDays(2L));

        List<Voting> votingList = Arrays.asList(voting1, voting2);

        List<VotingDTO> votingDTOList = votingMapper.listVotingToListVotingDTOs(votingList);

        assertNotNull(votingDTOList);
        assertEquals(2, votingDTOList.size());

        VotingDTO votingDTO1 = votingDTOList.getFirst();
        VotingDTO votingDTO2 = votingDTOList.getLast();

        assertEquals(voting1.getId(), new VotingId(votingDTO1.id()));
        assertEquals(voting1.getName(), votingDTO1.name());
        assertEquals(voting1.getDescription(), votingDTO1.description());
        assertEquals(voting1.getNumberOfParticipants(), votingDTO1.numberOfParticipants());
        assertEquals(voting1.getClosingDate(), votingDTO1.closingDate());
        assertEquals(voting1.isOpenToVote(), votingDTO1.isOpenToVote());
        assertEquals(voting2.getId(), new VotingId(votingDTO2.id()));
        assertEquals(voting2.getName(), votingDTO2.name());
        assertEquals(voting2.getDescription(), votingDTO2.description());
        assertEquals(voting2.getNumberOfParticipants(), votingDTO2.numberOfParticipants());
        assertEquals(voting2.getClosingDate(), votingDTO2.closingDate());
        assertEquals(voting2.isOpenToVote(), votingDTO2.isOpenToVote());
    }
}
