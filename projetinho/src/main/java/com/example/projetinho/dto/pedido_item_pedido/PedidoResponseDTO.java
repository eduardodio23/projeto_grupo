package com.example.projetinho.dto.pedido_item_pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;

    private LocalDate data;

    private String status;

    private Long clienteId;

    private BigDecimal total;

    private List<ItemPedidoDTO> itens;
}
