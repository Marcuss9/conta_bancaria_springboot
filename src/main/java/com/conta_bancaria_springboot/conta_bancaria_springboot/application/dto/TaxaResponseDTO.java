package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;


import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Taxa;

import java.math.BigDecimal;

public record TaxaResponseDTO(
        String id,
        String descricao,
        BigDecimal percentual,
        BigDecimal valorFixo
) {
    public static TaxaResponseDTO fromEntity(Taxa taxa) {
        return new TaxaResponseDTO(
                taxa.getId(),
                taxa.getDescricao(),
                taxa.getPercentual(),
                taxa.getValorFixo()
        );
    }
}