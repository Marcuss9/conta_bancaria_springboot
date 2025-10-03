package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class TipoDeContaInvalidaException extends RuntimeException {
    public TipoDeContaInvalidaException() {
        super("Tipo de conta inválida. Os tipos válidos são: 'CORRENTE' ou 'POUPANCA'");
    }
}
