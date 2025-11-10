package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.enums;

public enum StatusPagamento {
    SUCESSO,
    FALHA_SALDO_INSUFICIENTE,
    FALHA_BOLETO_VENCIDO,
    FALHA_CONTA_INATIVA,
    FALHA_TAXA_NAO_ENCONTRADA
}
