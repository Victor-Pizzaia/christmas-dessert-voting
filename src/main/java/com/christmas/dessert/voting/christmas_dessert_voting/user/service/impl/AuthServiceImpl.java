package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import com.christmas.dessert.voting.christmas_dessert_voting.infra.JwtProvider;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.User;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginRequest;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginResponse;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception.UserPasswordWrongException;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.AuthService;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.PasswordEncoderService;
import com.christmas.dessert.voting.christmas_dessert_voting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoderService passwordEncoderService;
    private final UserService userService;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Attempting to login user with identifier: {}", loginRequest.identifier());
        User user = userService.findUserByIdentifier(loginRequest.identifier());

        if (!passwordEncoderService.matches(loginRequest.password(), user.getPasswordHash())) {
            throw new UserPasswordWrongException("Wrong password");
        }

        String token = jwtProvider.generateToken(user.getId().id().toString(), loginRequest.identifier());
        long expirationTime = jwtProvider.getExpiresIn();
        return new LoginResponse(token, expirationTime);
    }
}
