package com.christmas.dessert.voting.christmas_dessert_voting.user.service;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;

public interface UserService {
    User findUserByIdentifier(String identifier);
}
