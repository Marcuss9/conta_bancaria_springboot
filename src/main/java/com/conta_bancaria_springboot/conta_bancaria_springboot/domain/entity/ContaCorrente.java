package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.SaldoInsuficienteException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ContaCorrente extends Conta{
    @Column(precision=19, scale=2)
    private BigDecimal limite = new BigDecimal("500.00");
    @Column(precision=19, scale=2)
    private BigDecimal taxa = new BigDecimal("0.05");

    @Override
    public String getTipo() {
        return "CORRENTE";
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValorMaiorQueZero(valor, "saque");

        BigDecimal custoSaque = valor.multiply(taxa);
        BigDecimal totalSaque = valor.add(custoSaque);

        if (this.getSaldo().add(limite).compareTo(totalSaque) < 0)
            throw new SaldoInsuficienteException();

        this.setSaldo(this.getSaldo().subtract(valor));
    }
}
