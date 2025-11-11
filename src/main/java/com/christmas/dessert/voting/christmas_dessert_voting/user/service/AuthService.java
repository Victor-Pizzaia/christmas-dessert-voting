package com.christmas.dessert.voting.christmas_dessert_voting.user.service;

import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginRequest;
import com.christmas.dessert.voting.christmas_dessert_voting.user.domain.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
