package com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;

import java.util.List;

public interface DessertUseCase {
    void registerDessert(DessertRequestDTO dessertRequestDTO, OwnerId ownerId);
    List<DessertDTO> findAllDesserts(OwnerId ownerId);
    DessertDTO findDessertByName(String dessertName);
    DessertDTO findDessertById(DessertId dessertId);
    void updateDessert(DessertId dessertId, DessertRequestDTO dessertRequestDTO, OwnerId ownerId);
}
