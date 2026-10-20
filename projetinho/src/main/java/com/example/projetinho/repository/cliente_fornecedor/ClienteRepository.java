package com.example.projetinho.repository.cliente_fornecedor;

import com.example.projetinho.entity.cliente_fornecedor.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByEmail(String email);
}