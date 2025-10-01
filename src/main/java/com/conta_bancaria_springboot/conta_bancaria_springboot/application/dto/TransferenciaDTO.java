package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import java.math.BigDecimal;

public record TransferenciaDTO(
    BigDecimal valor,
    String contaDestino
){
}
