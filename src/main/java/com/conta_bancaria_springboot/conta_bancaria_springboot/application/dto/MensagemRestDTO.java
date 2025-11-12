package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import lombok.Builder;

@Builder
public record MensagemRestDTO(
        String nome,
        String conteudo
) {
}
