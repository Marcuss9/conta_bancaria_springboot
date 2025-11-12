package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, String> {
}
