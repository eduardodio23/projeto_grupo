package com.example.projetinho.repository.pedido_item_pedido;

import com.example.projetinho.entity.pedido_item_pedido.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository <PedidoEntity, Long> {
    Optional <PedidoEntity> findBystatus (String status);

}
