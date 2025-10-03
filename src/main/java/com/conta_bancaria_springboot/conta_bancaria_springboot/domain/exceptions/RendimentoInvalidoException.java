package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class RendimentoInvalidoException extends RuntimeException {
    public RendimentoInvalidoException() {
        super("Rendimento deve ser aplicado somente em conta poupança.");
    }
}
