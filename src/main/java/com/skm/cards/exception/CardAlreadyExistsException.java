package com.skm.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class CardAlreadyExistsException extends RuntimeException{
    public CardAlreadyExistsException(String message) {
        super(message);
    }
}
