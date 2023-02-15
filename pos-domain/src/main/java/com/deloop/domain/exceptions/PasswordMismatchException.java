package com.deloop.domain.exceptions;


/**
 * Thrown when an invalid email address is encountered.
 */
public class PasswordMismatchException extends ApplicationException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
