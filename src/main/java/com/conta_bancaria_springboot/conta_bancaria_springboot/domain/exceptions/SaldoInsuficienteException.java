package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }
}
