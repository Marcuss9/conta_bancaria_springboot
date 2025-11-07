package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPublisher;
import org.springframework.stereotype.Service;

@Service
public class PublicadorService {

    @MqttPublisher("topico/teste")
    public String publicarMensagem() {
        return "Olá MQTT!";
    }
}
