package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoRequestDTO(
        @NotBlank(message = "O número da conta de origem é obrigatório")
        String numeroDaContaOrigem,

        @NotBlank(message = "O código do boleto é obrigatório")
        String boleto,

        @NotNull(message = "O valor a pagar é obrigatório")
        @Positive(message = "O valor do pagamento deve ser positivo")
        BigDecimal valorPago,

        @NotNull(message = "Informe o tipo do pagamento (Ex: BOLETO, PIX)")
        TipoPagamento tipoPagamento

        /* Lista de IDs das taxas que devem ser aplicadas
        @NotEmpty(message = "Pelo menos uma taxa deve ser informada (mesmo que seja uma taxa 'zero')")
        List<String> taxaIds*/
) {
}