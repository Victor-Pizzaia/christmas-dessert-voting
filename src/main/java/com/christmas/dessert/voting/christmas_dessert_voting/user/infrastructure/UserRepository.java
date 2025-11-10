package com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

}
