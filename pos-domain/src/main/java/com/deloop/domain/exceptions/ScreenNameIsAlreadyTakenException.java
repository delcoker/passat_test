package com.deloop.domain.exceptions;


/**
 * Thrown when the desired screen name is already taken.
 */
public class ScreenNameIsAlreadyTakenException extends EntityAlreadyExistsException {
    public ScreenNameIsAlreadyTakenException(String message) {
        super(message);
    }
}
