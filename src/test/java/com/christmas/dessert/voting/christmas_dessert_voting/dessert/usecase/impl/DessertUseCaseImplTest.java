package com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.Dessert;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.exception.DessertNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.infrastructure.DessertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DessertUseCaseImplTest {

    @Mock
    private DessertRepository dessertRepository;

    @InjectMocks
    private DessertUseCaseImpl dessertUseCase;

    @Test
    void shouldRegisterDessertSuccessfully() {
        DessertRequestDTO dessertRequestDTO = new DessertRequestDTO("Chocolate Cake", "Delicious chocolate cake");
        OwnerId ownerId = new OwnerId();

        dessertUseCase.registerDessert(dessertRequestDTO, ownerId);

        verify(dessertRepository).save(argThat(savedDessert ->
                savedDessert.getName().equals("Chocolate Cake") &&
                        savedDessert.getDescription().equals("Delicious chocolate cake") &&
                        savedDessert.getOwnerId().equals(ownerId)
        ));
    }

    @Test
    void shouldFindAllDessertsForOwner() {
        OwnerId ownerId = new OwnerId();
        List<Dessert> desserts = List.of(
                new Dessert(new DessertId(), ownerId, "Chocolate Cake", "Delicious"),
                new Dessert(new DessertId(), ownerId, "Apple Pie", "Tasty")
        );
        when(dessertRepository.findAllByOwnerId(ownerId)).thenReturn(desserts);

        dessertUseCase.findAllDesserts(ownerId);

        verify(dessertRepository).findAllByOwnerId(ownerId);
    }

    @Test
    void shouldThrowExceptionWhenDessertNotFoundByName() {
        String dessertName = "Nonexistent Dessert";
        when(dessertRepository.findByName(dessertName)).thenReturn(Optional.empty());

        assertThrows(DessertNotFoundException.class, () -> dessertUseCase.findDessertByName(dessertName));
    }

    @Test
    void shouldFindDessertByIdSuccessfully() {
        DessertId dessertId = new DessertId();
        Dessert dessert = new Dessert(dessertId, new OwnerId(), "Chocolate Cake", "Delicious");
        when(dessertRepository.findById(dessertId)).thenReturn(Optional.of(dessert));

        dessertUseCase.findDessertById(dessertId);

        verify(dessertRepository).findById(dessertId);
    }

    @Test
    void shouldUpdateDessertSuccessfully() {
        DessertId dessertId = new DessertId();
        OwnerId ownerId = new OwnerId();
        DessertRequestDTO dessertRequestDTO = new DessertRequestDTO("Updated Cake", "Updated description");
        Dessert existingDessert = new Dessert(dessertId, ownerId, "Old Cake", "Old description");
        when(dessertRepository.findById(dessertId)).thenReturn(Optional.of(existingDessert));

        dessertUseCase.updateDessert(dessertId, dessertRequestDTO, ownerId);

        verify(dessertRepository).save(argThat(updatedDessert ->
                updatedDessert.getName().equals("Updated Cake") &&
                        updatedDessert.getDescription().equals("Updated description")
        ));
    }
}
