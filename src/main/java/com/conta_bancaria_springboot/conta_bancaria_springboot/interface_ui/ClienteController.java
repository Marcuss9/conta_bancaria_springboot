package com.conta_bancaria_springboot.conta_bancaria_springboot.interface_ui;

import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteRegistroDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.dto.ClienteResponseDTO;
import com.conta_bancaria_springboot.conta_bancaria_springboot.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    public ClienteResponseDTO registrarCliente(@RequestBody ClienteRegistroDTO dto){
        return service.registrarClienteOuAnexarConta(dto);
    }
}
