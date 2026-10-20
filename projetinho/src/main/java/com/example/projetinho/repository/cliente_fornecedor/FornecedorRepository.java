package com.example.projetinho.repository.cliente_fornecedor;

import com.example.projetinho.entity.cliente_fornecedor.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    Optional<FornecedorEntity> findByEmail(String email);
}