package com.deloop.domain.exceptions;


/**
 * Thrown when the given confirmation token isn't valid any more.
 */
public class InvalidConfirmationTokenException extends ApplicationException {
    public InvalidConfirmationTokenException(String message) {
        super(message);
    }
}
