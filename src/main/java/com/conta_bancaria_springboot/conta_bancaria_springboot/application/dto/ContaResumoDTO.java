package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;



import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Conta;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.ContaCorrente;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.ContaPoupanca;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.EntidadeNaoEncontrada;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.TipoDeContaInvalidaException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaResumoDTO(
        @NotNull
        String numero,
        @NotNull
        String tipo,
        @NotNull
        BigDecimal saldo
) {
    public Conta toEntity(Cliente cliente){
        if("CORRENTE".equalsIgnoreCase(tipo)){
            return ContaCorrente.builder()
                    .numero(this.numero)
                    .numeroDaConta(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .limite(new BigDecimal("500.0"))
                    .taxa(new BigDecimal("0.05"))
                    .build();
        } else if ("POUPANCA".equalsIgnoreCase(tipo)){
            return ContaPoupanca.builder()
                    .numero(this.numero)
                    .numeroDaConta(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .rendimento(new BigDecimal("0.01"))
                    .build();
        }
        throw new TipoDeContaInvalidaException();
    }
    public static ContaResumoDTO fromEntity(Conta c) {
        return new ContaResumoDTO(
                c.getNumero(),
                c.getTipo(),
                c.getSaldo()
        );
    }
}

