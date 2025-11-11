package com.christmas.dessert.voting.christmas_dessert_voting.user.usecase;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;

public interface UserUseCase {
    UserDTO createUser(UserRequestDTO user);

    UserDTO findUserById(UserId userId);
}
