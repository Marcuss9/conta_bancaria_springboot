package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(
    @NotNull
    BigDecimal valor,
    @NotNull
    String contaDestino
){
}
