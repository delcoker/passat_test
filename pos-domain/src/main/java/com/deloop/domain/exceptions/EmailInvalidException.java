package com.deloop.domain.exceptions;


/**
 * Thrown when an invalid email address is encountered.
 */
public class EmailInvalidException extends ApplicationException {
    public EmailInvalidException(String message) {
        super(message);
    }
}
