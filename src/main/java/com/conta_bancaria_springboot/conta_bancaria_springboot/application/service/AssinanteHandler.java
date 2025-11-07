package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.rafaelcosta.spring_mqttx.domain.annotation.MqttSubscriber;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPayload;
import org.springframework.stereotype.Component;

@Component
public class AssinanteHandler {

    @MqttSubscriber("topico/teste")
    public void receberMensagem(@MqttPayload String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }
}
