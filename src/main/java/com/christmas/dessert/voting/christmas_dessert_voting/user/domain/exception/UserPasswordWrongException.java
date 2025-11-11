package com.christmas.dessert.voting.christmas_dessert_voting.user.domain.exception;

public class UserPasswordWrongException extends RuntimeException {
    public UserPasswordWrongException(String message) {
        super(message);
    }
}
