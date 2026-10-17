package com.example.projetinho.repository;

import com.example.projetinho.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
    Categoria findByNome(String nome);

}
