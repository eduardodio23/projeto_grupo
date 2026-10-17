package com.example.projetinho.dto.pedido_item_pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    private Long clienteId;

    private List<ItemPedidoDTO> itens;
}