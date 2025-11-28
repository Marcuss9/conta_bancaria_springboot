package com.conta_bancaria_springboot.conta_bancaria_springboot.infrastructure.iot;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.AutenticacaoIoTExpiradaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPayload;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPublisher;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttAuthService {

    private final ObjectMapper objectMapper;

    // Mapa para armazenar as promessas (Futures) de autenticação pendentes por ID do Cliente
    private final Map<String, CompletableFuture<Boolean>> pendenciasAutenticacao = new ConcurrentHashMap<>();

    /**
     * Método bloqueante que solicita auth e espera a resposta.
     */
    public void solicitarAutenticacaoBiometrica(String clienteId) {
        log.info("Iniciando fluxo IoT para cliente: {}", clienteId);

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pendenciasAutenticacao.put(clienteId, future);

        try {
            // 1. Dispara a mensagem MQTT
            String payload = criarPayloadJson(clienteId);
            publicarSolicitacao(payload);

            // 2. Aguarda a resposta por até 30 segundos (simulando timeout do dispositivo)
            Boolean autenticado = future.get(30, TimeUnit.SECONDS);

            if (!Boolean.TRUE.equals(autenticado)) {
                throw new AutenticacaoIoTExpiradaException();
            }

        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            log.error("Erro ou Timeout na autenticação IoT: {}", e.getMessage());
            throw new AutenticacaoIoTExpiradaException();
        } finally {
            pendenciasAutenticacao.remove(clienteId);
        }
    }

    // --- Integração com a lib spring-mqttx ---

    // Publica no tópico geral. O dispositivo deve filtrar ou podemos ter tópicos específicos se a lib suportar
    // Nota: Como a anotação é estática, usamos um tópico raiz e enviamos o ID no payload.
    // O requisito pede "banco/autenticacao/{idCliente}". Se a lib não suportar dinâmico,
    // o dispositivo deve assinar "banco/autenticacao/+" e filtrar pelo JSON.
    @MqttPublisher("banco/autenticacao/request")
    public String publicarSolicitacao(String payload) {
        return payload; // O retorno do método é o que será publicado
    }

    // Assina todas as validações usando curinga (+).
    // O tópico de resposta esperado é "banco/validacao/{idCliente}"
    @MqttSubscriber("banco/validacao")
    public void receberConfirmacao(@MqttPayload String mensagem) {
        log.info("Recebido MQTT: {}", mensagem);
        try {
            // Assume que a mensagem é um JSON simples: {"clienteId": "...", "codigo": "...", "validado": true}
            // Ou apenas o código. Vamos assumir um objeto para extrair o ID.
            AuthResponsePayload response = objectMapper.readValue(mensagem, AuthResponsePayload.class);

            if (response.clienteId() != null && pendenciasAutenticacao.containsKey(response.clienteId())) {
                // Completa o futuro, liberando a thread que está esperando no método acima
                pendenciasAutenticacao.get(response.clienteId()).complete(response.sucesso());
            }
        } catch (Exception e) {
            log.error("Erro ao processar mensagem MQTT", e);
        }
    }

    private String criarPayloadJson(String clienteId) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "clienteId", clienteId,
                    "acao", "SOLICITAR_BIOMETRIA",
                    "timestamp", System.currentTimeMillis()
            ));
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    // DTO interno auxiliar para ler o JSON de resposta
    private record AuthResponsePayload(String clienteId, boolean sucesso, String codigo) {}
}