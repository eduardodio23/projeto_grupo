package com.example.projetinho.dto.pedido_item_pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long id;

    private Long pedidoId;

    private Long produtoId;

    private Integer quantidade;

    private BigDecimal precoUnitario;

}
