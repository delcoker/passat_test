package com.deloop.domain.exceptions;


import lombok.extern.slf4j.Slf4j;

/**
 * Thrown when an invalid email address is encountered.
 */
@Slf4j
public class EmailNotFoundException extends ApplicationException {
    public EmailNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
