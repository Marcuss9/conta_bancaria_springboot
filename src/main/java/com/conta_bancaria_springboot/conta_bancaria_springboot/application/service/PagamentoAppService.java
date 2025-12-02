package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.PagamentoRequestDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.PagamentoResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Pagamento;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Taxa;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.StatusPagamento;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.EntidadeNaoEncontrada;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.PagamentoInvalidoException;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.SaldoInsuficienteException;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.TaxaInvalidaException;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.ContaRepository;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.PagamentoRepository;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.TaxaRepository;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.service.PagamentoDomainService;
import com.conta_bancaria_springboot.conta_bancaria_springboot.infrastructure.iot.MqttAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoAppService {

    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final TaxaRepository taxaRepository;
    private final PagamentoDomainService domainService;


    @Transactional(noRollbackFor = {SaldoInsuficienteException.class, PagamentoInvalidoException.class})
    @PreAuthorize("hasRole('CLIENTE')")
    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO dto, String emailAutenticado) {

        Conta conta = contaRepository.findByNumeroAndAtivaTrue(dto.numeroDaContaOrigem())
                .orElseThrow(() -> new EntidadeNaoEncontrada("Conta", "número", dto.numeroDaContaOrigem()));

        // Valida se a conta pertence ao usuário logado
        if (!conta.getCliente().getEmail().equals(emailAutenticado)) {
            // Lança uma exceção que será tratada pelo GlobalExceptionHandler
            throw new AccessDeniedException("O usuário autenticado não tem permissão para usar esta conta.");
        }

        // Busca automatica de taxas no banco
        List<Taxa> taxasAplicaveis = taxaRepository.findAllByTipoPagamentoAndAtivoTrue(dto.tipoPagamento());

        // Calcula o valor final
        BigDecimal valorFinal = domainService.calcularValorFinal(dto.valorPago(), taxasAplicaveis);
        StatusPagamento status;

        // Realizar o debito
        try {
            domainService.validarBoleto(dto.boleto());
            conta.debitar(valorFinal);
            status = StatusPagamento.SUCESSO;
            contaRepository.save(conta);

        } catch (SaldoInsuficienteException e) {
            status = StatusPagamento.FALHA_SALDO_INSUFICIENTE;
        } catch (PagamentoInvalidoException e) {
            status = StatusPagamento.FALHA_BOLETO_VENCIDO;
        }

        // Salva o registro do pagamento com Sucesso
        Pagamento pagamento = Pagamento.builder()
                .conta(conta)
                .boleto(dto.boleto())
                .valorPago(dto.valorPago())
                .valorTotalCobrado(valorFinal)
                .dataPagamento(LocalDateTime.now())
                .status(StatusPagamento.SUCESSO)
                .taxas(new HashSet<>(taxasAplicaveis))
                .tipo(dto.tipoPagamento())
                .build();

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        // Se o pagamento falhou, lançamos a exceção para o controller
        // (o pagamento já foi salvo como FALHA)
        if (status != StatusPagamento.SUCESSO) {
            if(status == StatusPagamento.FALHA_SALDO_INSUFICIENTE) throw new SaldoInsuficienteException();
            throw new PagamentoInvalidoException("Pagamento falhou. Motivo: " + status.name());
        }

        return PagamentoResponseDTO.fromEntity(pagamentoSalvo);
    }


     // Lista todos os pagamentos (sucesso ou falha) associados ao usuário autenticado.
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')") // Cliente pode ver o seu, Admin pode ver o de todos (se implementado)
    public List<PagamentoResponseDTO> listarPagamentosDoUsuario(String emailAutenticado) {
        // Se fosse um ADMIN, poderíamos ter uma lógica para listar todos
        // if (isAdmin) { return pagamentoRepository.findAll()... }

        // Para o CLIENTE, filtra pelo email
        return pagamentoRepository.findAllByClienteEmail(emailAutenticado).stream()
                .map(PagamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}