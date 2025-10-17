package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Gerente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.Role;
import lombok.Builder;


@Builder
public record GerenteDTO (
    String id,
    String nome,
    String cpf,
    String email,
    String senha,
    Boolean ativo,
    Role role
) {
        public static GerenteDTO fromEntity(Gerente gerente) {
            return GerenteDTO.builder()
                    .id(gerente.getId())
                    .nome(gerente.getNome())
                    .cpf(gerente.getCpf())
                    .email(gerente.getEmail())
                    .ativo(gerente.isAtivo())
                    .role(gerente.getRole())
                    .build();
        }

        public Gerente toEntity() {
            return Gerente.builder()
                    .id(this.id)
                    .nome(this.nome)
                    .cpf(this.cpf)
                    .email(this.email)
                    .senha(this.senha)
                    .ativo(this.ativo != null ? this.ativo : true)
                    .role(this.role != null ? this.role : Role.GERENTE)
                    .build();
        }
}
