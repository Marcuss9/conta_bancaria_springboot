package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class EntidadeNaoEncontrada extends RuntimeException {
    public EntidadeNaoEncontrada(String entidade, String atributo, String identificador) {
        super(entidade + " com " + atributo + " " + identificador + " não encontrado(a) ou inativo(a).");
    }
}
