package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class TransferenciaParaMesmaContaException extends RuntimeException {
    public TransferenciaParaMesmaContaException() {
        super("Não é possível transferir para a mesma conta.");
    }
}
