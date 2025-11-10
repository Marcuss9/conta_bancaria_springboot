package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class TaxaInvalidaException extends RuntimeException {
    public TaxaInvalidaException(String message) {
        super(message);
    }
}
