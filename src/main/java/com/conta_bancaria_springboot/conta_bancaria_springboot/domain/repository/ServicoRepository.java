package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
