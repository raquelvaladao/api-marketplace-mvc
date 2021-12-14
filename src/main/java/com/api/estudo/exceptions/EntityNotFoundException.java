package com.api.estudo.exceptions;

public class EntityNotFoundException extends LojaException{
    private static final long serialVersionUID = -7910192557077185968L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
