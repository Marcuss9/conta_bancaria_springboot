package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteRegistroDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteRegistroDTO dto){
        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toEntity())
        );

        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        return null;
    }
}
