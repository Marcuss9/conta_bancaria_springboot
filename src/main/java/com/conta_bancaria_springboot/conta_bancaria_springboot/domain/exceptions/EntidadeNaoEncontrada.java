package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class EntidadeNaoEncontrada extends RuntimeException {
    public EntidadeNaoEncontrada(String entidade) {
        super(entidade + "não existente ou inativa");
    }
}
