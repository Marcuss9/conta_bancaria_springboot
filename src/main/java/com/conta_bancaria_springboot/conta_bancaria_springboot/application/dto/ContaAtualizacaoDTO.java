package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO(
        @NotNull
        BigDecimal saldo,
        @NotNull
        BigDecimal limite,
        @NotNull
        BigDecimal rendimento,
        @NotNull
        BigDecimal taxa
) {
}
