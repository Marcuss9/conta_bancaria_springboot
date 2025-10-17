package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.controller;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.GerenteDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.service.GerenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class GerenteController {

    private final GerenteService service;

    @GetMapping
    public ResponseEntity<List<GerenteDTO>> listarTodosGerentes() {
        List<GerenteDTO> professores = service.listarTodosGerentes();
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    public ResponseEntity<GerenteDTO> cadastrarGerentes(@RequestBody GerenteDTO dto) {
        GerenteDTO gerenteCriado = service.cadastrarGerente(dto);
        return ResponseEntity.ok(gerenteCriado);
    }

}