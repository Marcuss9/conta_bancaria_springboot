/*package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Gerente;
import lombok.Builder;

import javax.management.relation.Role;

@Builder
public record GerenteDTO {
    String id,
    String nome,
    String cpf,
    String email,
    String senha,
    Boolean ativo,
    Role role
) {
        public static GerenteDTO fromEntity(Gerente gerente) {
            return ProfessorDTO.builder()
                    .id(professor.getId())
                    .nome(professor.getNome())
                    .cpf(professor.getCpf())
                    .email(professor.getEmail())
                    .ativo(professor.isAtivo())
                    .role(professor.getRole())
                    .build();
        }

        public Professor toEntity() {
            return Professor.builder()
                    .id(this.id)
                    .nome(this.nome)
                    .cpf(this.cpf)
                    .email(this.email)
                    .senha(this.senha)
                    .ativo(this.ativo != null ? this.ativo : true)
                    .role(this.role != null ? this.role : Role.PROFESSOR)
                    .build();
        }
}*/
