package com.deloop.domain.exceptions;


/**
 * Created by deloop on 26/04/2020.
 */
public class RedisSerializationException extends ApplicationException {

    public RedisSerializationException(String message) {
        super(message);
    }
}
