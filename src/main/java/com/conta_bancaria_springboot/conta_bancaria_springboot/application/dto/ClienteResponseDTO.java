package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;


import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ContaResumoDTO> contas
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                contas
        );
    }
}
