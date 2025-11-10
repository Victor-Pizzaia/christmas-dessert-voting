package com.christmas.dessert.voting.christmas_dessert_voting.user.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.christmas.dessert.voting.christmas_dessert_voting.user.service.PasswordEncoderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    private final BCryptPasswordEncoder encoder;

    public PasswordEncoderServiceImpl() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hash(String plainPassword) {
        log.info("Hashing password");
        return encoder.encode(plainPassword);
    }

    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        log.info("Checking password matches");
        return encoder.matches(plainPassword, hashedPassword);
    }
}
