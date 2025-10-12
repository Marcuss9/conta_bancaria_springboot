package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, String> {
    Optional<Gerente> findByEmail(String email);
}
