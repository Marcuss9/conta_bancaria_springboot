package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.TaxaRegistroDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.TaxaResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Taxa;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.EntidadeNaoEncontrada;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.TaxaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaxaService {

    private final TaxaRepository repository;

    @PreAuthorize("hasRole('ADMIN')")
    public TaxaResponseDTO registrarTaxa(TaxaRegistroDTO dto) {
        Taxa taxa = dto.toEntity();
        Taxa taxaSalva = repository.save(taxa);
        return TaxaResponseDTO.fromEntity(taxaSalva);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<TaxaResponseDTO> listarTaxas() {
        return repository.findAll().stream()
                .map(TaxaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public TaxaResponseDTO buscarTaxaPorId(String id) {
        Taxa taxa = getTaxaPorId(id);
        return TaxaResponseDTO.fromEntity(taxa);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TaxaResponseDTO atualizarTaxa(String id, TaxaRegistroDTO dto) {
        Taxa taxa = getTaxaPorId(id);

        taxa.setDescricao(dto.descricao());
        taxa.setPercentual(dto.percentual());
        taxa.setValorFixo(dto.valorFixo());

        Taxa taxaAtualizada = repository.save(taxa);
        return TaxaResponseDTO.fromEntity(taxaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletarTaxa(String id) {
        Taxa taxa = getTaxaPorId(id);
        taxa.setAtivo(false);
        repository.save(taxa);
    }

    private Taxa getTaxaPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontrada("Taxa", "ID", id));
    }
}