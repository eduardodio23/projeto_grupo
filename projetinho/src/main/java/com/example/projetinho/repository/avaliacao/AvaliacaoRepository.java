package com.example.projetinho.repository.avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>{
    List<Avaliacao> findByClienteId(Long clienteId);
    List<Avaliacao> findByProdutoId(Long pordutoId);
}
