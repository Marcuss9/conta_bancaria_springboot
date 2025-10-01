package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaAtualizacaoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaResumoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.TransferenciaDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ValorSaqueDepositoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.service.ContaService;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasContas() {
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @GetMapping("/{numeroDaConta}/buscar")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }

    @PutMapping("/{numeroDaConta}/atualizar")
    public ResponseEntity<ContaResumoDTO> atualizarConta (@PathVariable String numeroDaConta,
                                                          @RequestBody ContaAtualizacaoDTO dto){
        return ResponseEntity.ok(service.atualizarConta(numeroDaConta, dto));
    }

    @DeleteMapping("/{numeroDaConta}/deletar")
    public ResponseEntity<Void> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{numeroDaConta}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numeroDaConta,
                                                @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.sacar(numeroDaConta, dto));
    }

    @PostMapping("/{numeroDaConta}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numeroDaConta,
                                                    @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.depositar(numeroDaConta, dto));
    }

    @PostMapping("/{numeroDaConta}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numeroDaConta,
                                                     @RequestBody TransferenciaDTO dto){
        return ResponseEntity.ok(service.transferir(numeroDaConta, dto));
    }
}
