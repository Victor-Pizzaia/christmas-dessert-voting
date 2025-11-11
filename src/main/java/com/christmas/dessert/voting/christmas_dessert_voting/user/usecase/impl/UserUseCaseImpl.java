package com.christmas.dessert.voting.christmas_dessert_voting.user.usecase.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserNotFoundException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.infrastructure.UserRepository;
import com.christmas.dessert.voting.christmas_dessert_voting.user.mapper.UserMapper;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.PasswordEncoderService;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import com.christmas.dessert.voting.christmas_dessert_voting.user.usecase.UserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    @Override
    @Transactional
    public UserDTO createUser(@Valid UserRequestDTO userRequest) {
        log.info("Creating new user: {}", userRequest.name());
        String cpf = userRequest.cpf().replaceAll("\\D", "");
        log.info("Hashing password");
        String hashedPassword = passwordEncoderService.hash(userRequest.plainPassword());

        User newUser = new User(
                new UserId(),
                userRequest.name(),
                cpf,
                userRequest.email(),
                hashedPassword,
                userRequest.favoriteSweets());

        userRepository.save(newUser);
        log.info("Success on creating user: {}", newUser.getId());

        return new UserDTO(userRequest.name(), userRequest.favoriteSweets());
    }

    @Override
    public UserDTO findUserById(UserId userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId, 404));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
