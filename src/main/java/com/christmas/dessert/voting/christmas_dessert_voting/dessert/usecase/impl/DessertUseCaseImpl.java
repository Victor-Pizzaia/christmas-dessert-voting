package com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.exception.DessertNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.infrastructure.DessertRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.*;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.mapper.DessertMapper;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase.DessertUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DessertUseCaseImpl implements DessertUseCase {

    private final DessertRepository dessertRepository;

    @Override
    @Transactional
    public void registerDessert(DessertRequestDTO dessertRequestDTO, OwnerId ownerId) {
        Dessert newDessert = new Dessert(
                new DessertId(),
                ownerId,
                dessertRequestDTO.name(),
                dessertRequestDTO.description());

        dessertRepository.save(newDessert);
        log.debug("Success on registering dessert: {}", newDessert.getId());
    }

    @Override
    public List<DessertDTO> findAllDesserts(OwnerId ownerId) {
        List<Dessert> dessert = dessertRepository.findAllByOwnerId(ownerId);
        return DessertMapper.INSTANCE.dessertsToDessertDTOs(dessert);
    }

    @Override
    public DessertDTO findDessertByName(String dessertName) {
        Dessert dessert = dessertRepository.findByName(dessertName)
                .orElseThrow(() -> new DessertNotFoundException("Dessert not found: " + dessertName));
        return DessertMapper.INSTANCE.dessertToDessertDTO(dessert);
    }

    @Override
    public DessertDTO findDessertById(DessertId dessertId) {
        Dessert dessert = dessertRepository.findById(dessertId)
                .orElseThrow(() -> new DessertNotFoundException("Dessert not found: " + dessertId));
        return DessertMapper.INSTANCE.dessertToDessertDTO(dessert);
    }

    @Override
    @Transactional
    public void updateDessert(DessertId id, DessertRequestDTO dessertRequestDTO, OwnerId ownerId) {
        Dessert dessert = dessertRepository.findById(id)
                .orElseThrow(() -> new DessertNotFoundException("Dessert not found: " + id));
        dessert.setName(dessertRequestDTO.name());
        dessert.setDescription(dessertRequestDTO.description());

        dessertRepository.save(dessert);
    }
}
