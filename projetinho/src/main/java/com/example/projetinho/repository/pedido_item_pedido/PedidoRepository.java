package com.example.projetinho.repository.pedido_item_pedido;

import com.example.projetinho.entity.pedido_item_pedido.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    // Vários pedidos podem ter o mesmo status, por isso retorna lista
    List<PedidoEntity> findByStatus(String status);

    List<PedidoEntity> findByCliente_Id(Long clienteId);
}
