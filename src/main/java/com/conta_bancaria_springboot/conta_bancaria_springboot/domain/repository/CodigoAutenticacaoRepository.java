package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.CodigoAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoAutenticacaoRepository extends JpaRepository<CodigoAutenticacao, String> {
}
