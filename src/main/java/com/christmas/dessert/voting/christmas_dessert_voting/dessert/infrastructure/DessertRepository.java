package com.christmas.dessert.voting.christmas_dessert_voting.dessert.infrastructure;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.Dessert;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DessertRepository extends JpaRepository<Dessert, DessertId> {
    List<Dessert> findAllByOwnerId(OwnerId ownerId);
    Optional<Dessert> findByName(String name);
}
