package com.example.projetinho.repository;

import com.example.projetinho.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByQuantidade(int quantidade);
    Produto findByQuantidade(int quantidade);
    boolean existsByPreco(BigDecimal preco);
    Produto findByPreco(BigDecimal preco);
}
