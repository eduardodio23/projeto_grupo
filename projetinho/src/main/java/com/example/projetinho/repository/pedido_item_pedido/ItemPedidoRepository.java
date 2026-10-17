package com.example.projetinho.repository.pedido_item_pedido;

import com.example.projetinho.entity.pedido_item_pedido.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository
        extends JpaRepository<ItemPedidoEntity, Long> {

    List<ItemPedidoEntity> findByPedido_Id(Long pedidoId);
}