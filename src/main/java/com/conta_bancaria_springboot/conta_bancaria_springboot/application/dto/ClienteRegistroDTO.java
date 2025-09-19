package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;

import java.util.ArrayList;

public record ClienteRegistroDTO(
        String nome,
        String cpf,
        ContaResumoDTO contaDTO
) {
    public Cliente toEntity(){
        return Cliente.builder()
                .ativo(true)
                .nome(this.nome)
                .cpf(this.cpf)
                .contas(new ArrayList<Conta>())
                .build();
    }
}
