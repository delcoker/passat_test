package com.deloop.domain.exceptions;


import lombok.extern.slf4j.Slf4j;

/**
 * Created by deloop on 26/04/2020.
 */
@Slf4j
public class ApplicationException extends Exception {
    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
        log.error(message);
    }
}
