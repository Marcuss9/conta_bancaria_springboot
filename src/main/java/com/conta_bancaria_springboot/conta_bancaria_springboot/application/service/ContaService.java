package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaAtualizacaoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ContaResumoDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.ContaCorrente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.ContaPoupanca;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }

    public ContaResumoDTO atualizarConta(String numeroConta, ContaAtualizacaoDTO dto) {
        var conta = buscaContaAtivaPorNumero(numeroConta);

        conta.setSaldo(dto.saldo());

        if (conta instanceof ContaPoupanca poupanca) {
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        }
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public void deletarConta(String numeroDaConta) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        conta.setAtiva(false);
        repository.save(conta);
    }

    private Conta buscaContaAtivaPorNumero(String numeroDaConta) {
        var conta = repository.findByNumeroAndAtivaTrue(numeroDaConta).orElseThrow(
                () -> new RuntimeException("Conta não encontrada")
        );
        return conta;
    }

    public ContaResumoDTO sacar(String numeroConta, BigDecimal valor) {
        var conta = buscaContaAtivaPorNumero(numeroConta);
        conta.sacar(valor);
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
}
