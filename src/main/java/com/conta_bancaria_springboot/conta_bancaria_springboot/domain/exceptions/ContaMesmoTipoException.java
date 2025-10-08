package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class ContaMesmoTipoException extends RuntimeException {
    public ContaMesmoTipoException() {
        super("O cliente já possui uma conta do mesmo tipo.");
    }
}
