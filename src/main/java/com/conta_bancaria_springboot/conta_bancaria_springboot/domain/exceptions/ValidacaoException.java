package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;


public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}