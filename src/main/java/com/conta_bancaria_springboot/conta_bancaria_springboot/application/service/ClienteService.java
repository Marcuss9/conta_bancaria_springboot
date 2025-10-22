package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;


import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteRegistroDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.ContaMesmoTipoException;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.EntidadeNaoEncontrada;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN')")
    public ClienteResponseDTO registarClienteOuAnexarConta(ClienteRegistroDTO dto) {

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toEntity())
        );
        cliente.setSenha(encoder.encode(dto.senha()));
        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva());

        if(jaTemTipo) throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public ClienteResponseDTO atualizarCliente(String cpf, ClienteRegistroDTO dto) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setSenha(dto.senha());
        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletarCliente(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        cliente.setAtivo(false);
        cliente.getContas().forEach(conta -> conta.setAtiva(false));
        repository.save(cliente);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    private Cliente buscarPorCpfClienteAtivo(String cpf) {
        return repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new EntidadeNaoEncontrada("Cliente")
        );
    }
}