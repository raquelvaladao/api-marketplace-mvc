package com.api.estudo.exceptions;

public class UsuarioNaoPermitido extends LojaException {

    private static final long serialVersionUID = 7195874217380195257L;

    public UsuarioNaoPermitido(String message) {
        super(message);
    }
}
