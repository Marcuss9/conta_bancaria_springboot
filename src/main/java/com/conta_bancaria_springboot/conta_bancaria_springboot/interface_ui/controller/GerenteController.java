/*package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.controller;

import com.senai.modelo_autenticacao_autorizacao.application.dto.ProfessorDTO;
import com.senai.modelo_autenticacao_autorizacao.application.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class GerenteController {

    private final ProfessorService service;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarTodosProfessores() {
        List<ProfessorDTO> professores = service.listarTodosProfessores();
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody ProfessorDTO dto) {
        ProfessorDTO professorCriado = service.cadastrarProfessor(dto);
        return ResponseEntity.ok(professorCriado);
    }

}
*/