package com.deloop.domain.exceptions;


/**
 * Created by del on 26/04/2020.
 */
public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
