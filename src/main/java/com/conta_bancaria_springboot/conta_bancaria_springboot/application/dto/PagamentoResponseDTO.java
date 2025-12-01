package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Pagamento;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.StatusPagamento;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PagamentoResponseDTO(
        String id,
        String numeroDaConta,
        String boleto,
        BigDecimal valorPago,
        BigDecimal valorTotalCobrado,
        LocalDateTime dataPagamento,
        StatusPagamento status,
        TipoPagamento tipo,
        List<TaxaResponseDTO> taxasAplicadas
) {
    public static PagamentoResponseDTO fromEntity(Pagamento pagamento) {
        List<TaxaResponseDTO> taxasDTO = pagamento.getTaxas().stream()
                .map(TaxaResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getConta().getNumeroDaConta(),
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getValorTotalCobrado(),
                pagamento.getDataPagamento(),
                pagamento.getStatus(),
                pagamento.getTipo(),
                taxasDTO
        );
    }
}