package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaxaRepository extends JpaRepository<Taxa, String> {
    List<Taxa> findAllByAtivoTrue();
    Optional<Taxa> findByIdAndAtivoTrue(String id);
}
