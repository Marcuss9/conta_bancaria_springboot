package com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto;

public class AuthDTO {

    public record LoginRequest(
            String email,
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}
