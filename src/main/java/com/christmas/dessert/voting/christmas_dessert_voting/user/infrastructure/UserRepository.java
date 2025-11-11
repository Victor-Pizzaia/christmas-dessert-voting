package com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.cpf = :identifier")
    Optional<User> findUserByEmailOrCpf(@Param("identifier") String identifier);
}
