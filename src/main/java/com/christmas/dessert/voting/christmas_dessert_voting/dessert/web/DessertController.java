package com.christmas.dessert.voting.christmas_dessert_voting.dessert.web;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase.DessertUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/desserts")
public class DessertController {

    private final DessertUseCase dessertUseCase;

    @PostMapping
    public ResponseEntity<Void> registerDessert(Authentication authentication, @Validated @RequestBody DessertRequestDTO dessertRequestDTO) {
        log.info("Register new dessert request received");
        dessertUseCase.registerDessert(dessertRequestDTO, getOwnerId(authentication));
        log.info("{} was registered with successfully", dessertRequestDTO.name());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DessertDTO>> getDesserts(Authentication authentication) {
        log.info("Getting desserts information for user");
        List<DessertDTO> retrievedDesserts = dessertUseCase.findAllDesserts(getOwnerId(authentication));
        log.info("Desserts retrieved successfully");
        return new ResponseEntity<>(retrievedDesserts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DessertDTO> getDessertById(@PathVariable("id") DessertId dessertId) {
        log.info("Getting desserts information by id");
        DessertDTO retrievedDessert = dessertUseCase.findDessertById(dessertId);
        return new ResponseEntity<>(retrievedDessert, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<DessertDTO> getDessertByName(@RequestParam("name") String dessertName) {
        log.info("Getting dessert information by name {}", dessertName);
        DessertDTO retrievedDessert = dessertUseCase.findDessertByName(dessertName);
        return new ResponseEntity<>(retrievedDessert, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateDessert(Authentication authentication,
                                              @PathVariable("id") DessertId id,
                                              @Validated @RequestBody DessertRequestDTO dessertRequestDTO) {
        log.info("Update dessert request received");
        dessertUseCase.updateDessert(id, dessertRequestDTO, getOwnerId(authentication));
        log.info("{} was updated successfully", dessertRequestDTO.name());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static OwnerId getOwnerId(Authentication authentication) {
        String ownerId = authentication.getName();
        return new OwnerId(ownerId);
    }
}
