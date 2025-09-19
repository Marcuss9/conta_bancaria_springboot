package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ContaResumoDTO> contas
        ) {
}
