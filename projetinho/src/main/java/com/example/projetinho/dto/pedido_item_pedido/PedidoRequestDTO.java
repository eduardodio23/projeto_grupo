package com.example.projetinho.dto.pedido_item_pedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    // @Valid faz a validação descer para cada item da lista
    @Valid
    @NotEmpty(message = "O pedido precisa ter pelo menos um item.")
    private List<ItemPedidoDTO> itens;
}
