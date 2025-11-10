package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OperacaoDTO(
        @NotNull
        BigDecimal valor
) {
}
