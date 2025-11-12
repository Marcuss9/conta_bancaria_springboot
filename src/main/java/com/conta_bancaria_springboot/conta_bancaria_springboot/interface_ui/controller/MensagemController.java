package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.controller;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.MensagemMqttDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.MensagemRestDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.service.MensagemService;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPayload;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPublisher;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
public class MensagemController {
    private final MensagemService mensagemService;
    @PostMapping
    @MqttPublisher("topico/mensagem/rest")
    public MensagemMqttDTO enviarMensagem(@RequestBody MensagemRestDTO mensagemRestDTO) {

        return mensagemService.enviarMensagem(mensagemRestDTO);
    }

    @MqttSubscriber("topico/mensagem/mqtt")
    public void receberMensagem(@MqttPayload MensagemMqttDTO mensagemMqttDTO) {
        mensagemService.salvarMensagemRecebida(mensagemMqttDTO);
    }
}
