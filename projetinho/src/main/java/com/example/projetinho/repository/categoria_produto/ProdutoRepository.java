package com.example.projetinho.repository.categoria_produto;

import com.example.projetinho.entity.categoria_produto.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    // Vários produtos podem ter a mesma quantidade/preço, por isso retornam lista
    boolean existsByQuantidade(Integer quantidade);
    List<ProdutoEntity> findByQuantidade(Integer quantidade);
    boolean existsByPreco(BigDecimal preco);
    List<ProdutoEntity> findByPreco(BigDecimal preco);
}
