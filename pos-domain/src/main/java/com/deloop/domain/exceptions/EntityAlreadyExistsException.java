package com.deloop.domain.exceptions;


/**
 * Created by deloop on 26/04/2020.
 */
public class EntityAlreadyExistsException extends ApplicationException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
