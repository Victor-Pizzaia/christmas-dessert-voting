package com.christmas.dessert.voting.christmas_dessert_voting.voting.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.Voting;
import com.christmas.dessert.voting.christmas_dessert_voting.voting.domain.VotingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface VotingMapper {
    VotingMapper INSTANCE = Mappers.getMapper( VotingMapper.class );
    @Mapping(target = "id", source = "id.id")
    VotingDTO votingToVotingDTO(Voting voting);
    List<VotingDTO> listVotingToListVotingDTOs(List<Voting> voting);
}
