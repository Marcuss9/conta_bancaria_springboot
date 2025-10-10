package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.exception;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex,
                                                       HttpServletRequest request) {
        return ProblemDetailUtils.buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "Um ou mais campos são inválidos",
                request.getRequestURI()
        );

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ProblemDetail handleConversionFailed(ConversionFailedException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Falha de conversão de parâmetro");
        problem.setDetail("Um parâmetro não pôde ser convertido para o tipo esperado.");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("error", ex.getMessage());
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erro de validação nos parâmetros");
        problem.setDetail("Um ou mais parâmetros são inválidos");
        problem.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String campo = violation.getPropertyPath().toString();
            String mensagem = violation.getMessage();
            errors.put(campo, mensagem);
        });
        problem.setProperty("errors", errors);
        return problem;
    }
}
