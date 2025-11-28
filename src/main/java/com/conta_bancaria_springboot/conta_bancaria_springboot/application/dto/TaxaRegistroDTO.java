package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Taxa;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record TaxaRegistroDTO(
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotNull(message = "O percentual é obrigatório")
        @PositiveOrZero(message = "O percentual não pode ser negativo")
        BigDecimal percentual,

        @NotNull(message = "O valor fixo é obrigatório")
        @PositiveOrZero(message = "O valor fixo não pode ser negativo")
        BigDecimal valorFixo,

        @NotNull(message = "O tipo de pagamento é obrigatório")
        TipoPagamento tipoPagamento
) {
    public Taxa toEntity() {
        return Taxa.builder()
                .descricao(this.descricao)
                .percentual(this.percentual)
                .valorFixo(this.valorFixo)
                .tipoPagamento(this.tipoPagamento)
                .ativo(true)
                .build();
    }
}
