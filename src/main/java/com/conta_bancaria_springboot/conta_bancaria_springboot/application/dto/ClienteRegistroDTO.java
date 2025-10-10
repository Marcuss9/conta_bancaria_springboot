package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;



import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;

public record ClienteRegistroDTO(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]*$", message = "Nome deve conter apenas letras, espaços, apóstrofos ou hífens.")
        String nome,
        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF fornecido é inválido.")
        String cpf,
        @NotNull(message = "Os dados da conta são obrigatórios.")
        @Valid
        ContaResumoDTO contaDTO
) {
    public Cliente toEntity() {
        return Cliente.builder()
                .ativo(true)
                .nome(this.nome)
                .cpf(this.cpf)
                .contas(new ArrayList<Conta>())
                .build();
    }
}
