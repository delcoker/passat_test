package com.deloop.domain.exceptions;


/**
 * Thrown when the given user doesn't exist.
 */
//@RequiredArgsConstructor
public class NoSuchUserException extends EntityNotFoundException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
