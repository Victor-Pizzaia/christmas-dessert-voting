package com.christmas.dessert.voting.christmas_dessert_voting.dessert.mapper;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.Dessert;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DessertMapperTest {

    private final DessertMapper dessertMapper = DessertMapper.INSTANCE;

    private Dessert dessert;

    @BeforeEach
    public void setUp() {
        this.dessert = new Dessert(new DessertId(), new OwnerId(), "Chocolate Cake", "Delicious");
    }

    @Test
    void shouldMapDessertToDessertDTO() {
        DessertDTO dessertDTO = dessertMapper.dessertToDessertDTO(dessert);

        assertNotNull(dessertDTO);
        assertEquals(dessert.getId().id().toString(), dessertDTO.id());
        assertEquals(dessert.getName(), dessertDTO.name());
        assertEquals(dessert.getDescription(), dessertDTO.description());
    }

    @Test
    void shouldMapEmptyDessertListToEmptyDessertDTOList() {
        List<Dessert> desserts = List.of(dessert);

        List<DessertDTO> dessertDTOs = dessertMapper.dessertsToDessertDTOs(desserts);

        assertNotNull(dessertDTOs);
        assertEquals(1, dessertDTOs.size());
    }

    @Test
    void shouldMapDessertListToDessertDTOList() {
        Dessert dessert2 = new Dessert(new DessertId(), new OwnerId(), "Apple Pie", "Tasty");
        List<Dessert> desserts = List.of(dessert, dessert2);

        List<DessertDTO> dessertDTOs = dessertMapper.dessertsToDessertDTOs(desserts);

        DessertDTO dessertDTO1 = dessertDTOs.getFirst();
        DessertDTO dessertDTO2 = dessertDTOs.getLast();

        assertNotNull(dessertDTOs);
        assertEquals(2, dessertDTOs.size());
        assertEquals(dessert.getId().id().toString(), dessertDTO1.id());
        assertEquals(dessert.getName(), dessertDTO1.name());
        assertEquals(dessert.getDescription(), dessertDTO1.description());
        assertEquals(dessert2.getId().id().toString(), dessertDTO2.id());
        assertEquals(dessert2.getName(), dessertDTO2.name());
        assertEquals(dessert2.getDescription(), dessertDTO2.description());
    }
}
