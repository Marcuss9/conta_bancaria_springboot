package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaAtualizacaoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaResumoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.service.ContaService;
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

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }

    @PutMapping("/{numeroConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta (@PathVariable String numeroDaConta,
                                                          @RequestBody ContaAtualizacaoDTO dto){
        return ResponseEntity.ok(service.atualizarConta(numeroDaConta, dto));
    }

    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{numeroConta}")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numeroConta,
                                                @RequestParam BigDecimal valor){
        return ResponseEntity.ok(service.sacar(numeroConta, valor));
    }
}
