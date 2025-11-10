package com.conta_bancaria_springboot.conta_bancaria_springboot.domain.repository;

import com.conta_bancaria_springboot.conta_bancaria_springboot.domain.entity.DispositivoIoT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoIoTRepository extends JpaRepository<DispositivoIoT, String> {
}
