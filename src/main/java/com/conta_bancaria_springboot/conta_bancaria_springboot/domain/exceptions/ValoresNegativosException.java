package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions;

public class ValoresNegativosException extends RuntimeException {
  public ValoresNegativosException(String operacao) {
    super("Não é possível realizar a operação de" +
            operacao + "com valores negativos");
  }
}
