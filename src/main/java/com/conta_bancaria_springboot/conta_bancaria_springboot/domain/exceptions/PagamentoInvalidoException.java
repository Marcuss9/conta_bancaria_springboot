package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String message) {
        super(message);
    }
}
