package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class AutenticacaoIoTExpiradaException extends ErrorResponseException {
    public AutenticacaoIoTExpiradaException() {
        super(HttpStatus.UNAUTHORIZED, asProblemDetail(), null);
    }

    private static ProblemDetail asProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "A autenticação biométrica via IoT expirou ou falhou."
        );
        problemDetail.setTitle("Autenticação IoT Falhou");
        problemDetail.setType(URI.create("https://banco-api.com/erros/autenticacao-iot"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}