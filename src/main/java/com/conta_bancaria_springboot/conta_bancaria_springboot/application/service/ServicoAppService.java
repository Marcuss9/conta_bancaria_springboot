package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ServicoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Servico;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.EntidadeNaoEncontrada;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoAppService {

    private final ServicoRepository repository;

    public ServicoAppService(ServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoDTO salvar(ServicoDTO dto) {
        Servico servico = dto.toEntity();
        servico.validar();
        return ServicoDTO.fromEntity(repository.save(servico));
    }

    public List<ServicoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ServicoDTO::fromEntity)
                .toList();
    }

    public ServicoDTO buscarPorId(Long id) {
        return ServicoDTO.fromEntity(
                repository.findById(id)
                        .orElseThrow(() -> new EntidadeNaoEncontrada("Serviço com ID " + id + " não encontrado."))
        );
    }

    public ServicoDTO atualizar(Long id, ServicoDTO dtoAtualizado) {
        Servico existente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontrada("Serviço com ID " + id + " não encontrado."));

        Servico atualizado = dtoAtualizado.toEntity();
        atualizado.setId(existente.getId());

        atualizado.validar();
        return ServicoDTO.fromEntity(repository.save(atualizado));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontrada("Serviço com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }

}
