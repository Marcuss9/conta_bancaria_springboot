package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;


import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Transactional
public record ClienteResponseDTO(
        @NotBlank
        String id,
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]*$", message = "Nome deve conter apenas letras, espaços, apóstrofos ou hífens.")
        String nome,
        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF fornecido é inválido.")
        String cpf,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        List<ContaResumoDTO> contas
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getSenha(),
                contas
        );
    }
}
