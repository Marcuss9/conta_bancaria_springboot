package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.StatusPagamento;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_conta"))
    private Conta conta;

    @Column(nullable = false)
    private String boleto;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal valorPago;

    @Column(nullable = false)
    private BigDecimal valorTotalCobrado;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipo;

    @ManyToMany
    @JoinTable(
            name = "pagamento_taxa",
            joinColumns = @JoinColumn(name = "pagamento_id"),
            inverseJoinColumns = @JoinColumn(name = "taxa_id")
    )
    private Set<Taxa> taxas; // Set: evita duplicatas

    //todo adicionar tipo de pagamento
}
