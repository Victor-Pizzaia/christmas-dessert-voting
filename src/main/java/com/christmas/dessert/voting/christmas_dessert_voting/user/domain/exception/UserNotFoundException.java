package com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
