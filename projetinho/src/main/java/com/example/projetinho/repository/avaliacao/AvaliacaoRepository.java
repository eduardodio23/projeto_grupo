package com.example.projetinho.repository.avaliacao;

import com.example.projetinho.entity.avaliacao.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {
    List<AvaliacaoEntity> findByCliente_Id(Long clienteId);
    List<AvaliacaoEntity> findByProduto_Id(Long produtoId);
    boolean existsByCliente_IdAndProduto_Id(Long clienteId, Long produtoId);
}
