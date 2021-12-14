package com.api.estudo.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaException extends RuntimeException{

    private static final long serialVersionUID = -1505353848714979625L;
    private String message;

    public LojaException(String message) {
        super(message);
        this.message = message;
    }
}
