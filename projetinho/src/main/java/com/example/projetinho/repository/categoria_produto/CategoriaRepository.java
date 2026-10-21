package com.example.projetinho.repository.categoria_produto;

import com.example.projetinho.entity.categoria_produto.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    boolean existsByNome(String nome);
    CategoriaEntity findByNome(String nome);

}
