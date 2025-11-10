package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui.controller;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.PagamentoRequestDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.PagamentoResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.service.PagamentoAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Tag(name = "Pagamentos", description = "Operações relacionadas a pagamentos de boletos e contas")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoAppService service;

    @Operation(
            summary = "Realizar um pagamento",
            description = "Processa um pagamento de boleto debitando o valor (principal + taxas) da conta do cliente. Requer permissão de CLIENTE.",
            requestBody = @RequestBody(
                    description = "Dados do pagamento a ser realizado",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PagamentoRequestDTO.class),
                            examples = @ExampleObject(name = "Pagamento Exemplo",
                                    value = """
                                              {
                                                "numeroDaContaOrigem": "1234567890",
                                                "boleto": "34191.79001 01043.510047 91020.150008 8 76210000050000",
                                                "valorPago": 500.00,
                                                "taxaIds": ["taxa1", "taxa2"]
                                              }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento realizado com SUCESSO"),
                    @ApiResponse(responseCode = "400", description = "Falha no pagamento (Ex: Saldo insuficiente, Boleto vencido, Dados inválidos)",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "Saldo Insuficiente",
                                            value = """
                                                      {
                                                        "type": "https://example.com/probs/saldo-insuficiente",
                                                        "title": "Saldo insuficiente.",
                                                        "status": 400,
                                                        "detail": "Saldo insuficiente para realizar a operação.",
                                                        "instance": "/api/pagamentos"
                                                      }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Conta não pertence ao usuário)"),
                    @ApiResponse(responseCode = "404", description = "Conta ou Taxa não encontrada")
            }
    )
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(
            @Valid @RequestBody PagamentoRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Passamos o email (username) do usuário autenticado para o service
        String emailAutenticado = userDetails.getUsername();
        PagamentoResponseDTO response = service.realizarPagamento(dto, emailAutenticado);

        return ResponseEntity.created(
                URI.create("/api/pagamentos/" + response.id())
        ).body(response);
    }

    @Operation(
            summary = "Listar meus pagamentos",
            description = "Retorna uma lista de todos os pagamentos (com sucesso ou falha) associados à conta do cliente autenticado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    @GetMapping("/meus-pagamentos")
    public ResponseEntity<List<PagamentoResponseDTO>> listarMeusPagamentos(
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailAutenticado = userDetails.getUsername();
        List<PagamentoResponseDTO> pagamentos = service.listarPagamentosDoUsuario(emailAutenticado);
        return ResponseEntity.ok(pagamentos);
    }
}