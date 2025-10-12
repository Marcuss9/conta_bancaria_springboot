package com.conta_bancaria_springboot.conta_bancaria_springboot.application.service;

//import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
//import com.senai.modelo_autenticacao_autorizacao.domain.UsuarioNaoEncontradoException;
//import com.senai.modelo_autenticacao_autorizacao.domain.entity.Usuario;
//import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
//import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.JwtService;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.AuthDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Usuario;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.exceptions.UsuarioNaoEncontradoException;
import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository.UsuarioRepository;
import com.conta_bancaria_springboot.conta_bancaria_springboot.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getRole().name());
    }
}


