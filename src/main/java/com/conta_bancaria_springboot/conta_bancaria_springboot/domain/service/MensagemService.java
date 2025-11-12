package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.MensagemMqttDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.MensagemRestDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MensagemService {
    private final MensagemRepository mensagemRepository;
    public MensagemMqttDTO enviarMensagem(MensagemRestDTO mensagemRestDTO) {
        return MensagemMqttDTO.builder()
                .nome(mensagemRestDTO.nome())
                .conteudo(mensagemRestDTO.conteudo())
                .build();
    }

    public void salvarMensagemRecebida(MensagemMqttDTO mensagemMqttDTO) {

        mensagemRepository.save(MensagemMqttDTO.toEntity(mensagemMqttDTO));
    }
}
