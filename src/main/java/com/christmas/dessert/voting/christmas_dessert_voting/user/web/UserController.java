package com.christmas.dessert.voting.christmas_dessert_voting.user.web;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserId;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.UserRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.user.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Create user request received");
        UserDTO newUser = userUseCase.createUser(userRequestDTO);
        log.info("User created successfully: {}", newUser.name());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        log.info("Getting user information request received");
        String userId = authentication.getName();

        return new ResponseEntity<>(userUseCase.getUser(new UserId(userId)), HttpStatus.OK);
    }
}
