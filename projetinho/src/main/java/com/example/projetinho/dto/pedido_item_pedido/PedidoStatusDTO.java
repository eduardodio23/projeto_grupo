package com.example.projetinho.dto.pedido_item_pedido;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoStatusDTO {

    // PENDENTE, PAGO, ENVIADO, ENTREGUE ou CANCELADO
    @NotBlank(message = "O status é obrigatório.")
    private String status;
}
