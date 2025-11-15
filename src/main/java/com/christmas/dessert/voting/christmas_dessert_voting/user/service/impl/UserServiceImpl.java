package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure.UserRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByIdentifier(String identifier) {
        return userRepository.findUserByEmailOrCpf(identifier)
                .orElseThrow(() -> new UserNotFoundException("User %s not found".formatted(identifier)));
    }
}
