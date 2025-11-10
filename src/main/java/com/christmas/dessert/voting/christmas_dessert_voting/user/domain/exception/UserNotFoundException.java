package com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception;

public class UserNotFoundException extends RuntimeException {
    private final String message;
    private final int code;

    public UserNotFoundException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
