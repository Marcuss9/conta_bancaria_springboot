package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Mensagem;
import lombok.Builder;

@Builder
public record MensagemMqttDTO(
        String nome,
        String conteudo
) {
    public static Mensagem toEntity(MensagemMqttDTO mensagemMqttDTO) {
        return Mensagem.builder()
                .nome(mensagemMqttDTO.nome())
                .conteudo(mensagemMqttDTO.conteudo())
                .build();
    }
}
