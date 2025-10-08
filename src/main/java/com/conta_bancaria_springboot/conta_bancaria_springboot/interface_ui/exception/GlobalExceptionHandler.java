package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.exception;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValoresNegativosException.class)
    public ProblemDetail handleValoresNegativo(ValoresNegativosException ex,
                                               HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Valores negativos não são permitidos.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EntidadeNaoEncontrada.class)
    public ProblemDetail handleEntidadeNaoEncontrada(EntidadeNaoEncontrada ex,
                                                     HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Entidade não encontrada.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // Retorna 422 Unprocessable Entity para violações de regras de negócio.
// A requisição é válida, mas não pode ser processada.
    @ExceptionHandler(TransferenciaParaMesmaContaException.class)
    public ProblemDetail handleTransferenciaParaMesmaContaException(TransferenciaParaMesmaContaException ex,
                                                                    HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Não é possível transferir para a mesma conta",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TipoDeContaInvalidaException.class)
    public ProblemDetail handleTipoDeContaInvalida(TipoDeContaInvalidaException ex,
                                                            HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Tipo de conta inválida.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Trata exceções de saldo insuficiente para realizar uma transação.
     *
     * Retorna HTTP 402 (Payment Required), o código semanticamente mais correto
     * para indicar que a operação não pode ser concluída por falta de fundos.
     * A requisição em si é válida, mas requer uma pré-condição financeira
     * (saldo) que não foi atendida.
     */
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ProblemDetail handleSaldoInsuficienteException(SaldoInsuficienteException ex,
                                                                   HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.PAYMENT_REQUIRED,
                "Saldo insuficiente.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Trata exceções onde uma operação (aplicar rendimento) é incompatível
     * com o tipo da conta.
     *
     * Retorna HTTP 409 (Conflict) para indicar que a requisição conflita com o
     * estado atual do recurso. Neste caso, o tipo da conta (ex: 'CORRENTE')
     * impede a aplicação de rendimento.
     */
    @ExceptionHandler(RendimentoInvalidoException.class)
    public ProblemDetail handleRendimentoInvalidoException(RendimentoInvalidoException ex,
                                                                    HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.CONFLICT,
                "Rendimento inválido para Conta Corrente.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Trata exceções onde o cliente já possui uma conta do tipo que está
     * tentando criar.
     *
     * Retorna HTTP 409 (Conflict) para indicar que a requisição de criação
     * não pode ser processada, pois viola uma regra de unicidade e entra em
     * conflito com um recurso já existente no sistema.
     */
    @ExceptionHandler(ContaMesmoTipoException.class)
    public ProblemDetail handleContaMesmoTipoException(ContaMesmoTipoException ex,
                                                                HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.CONFLICT,
                "Não é possível criar uma conta do mesmo tipo.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
