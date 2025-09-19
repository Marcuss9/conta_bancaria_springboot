package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);
}
