package com.christmas.dessert.voting.christmas_dessert_voting.dessert.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.Dessert;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DessertMapper {
    DessertMapper INSTANCE = Mappers.getMapper( DessertMapper.class );
    @Mapping(target = "id", source = "dessert.id.id")
    @Mapping(target = "name", source = "dessert.name")
    @Mapping(target = "description", source = "dessert.description")
    DessertDTO dessertToDessertDTO(Dessert dessert);
    List<DessertDTO> dessertsToDessertDTOs(List<Dessert> desserts);
}
