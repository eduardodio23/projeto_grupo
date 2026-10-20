package com.example.projetinho.dto.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoRequestDTO (
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O ID do produto é obrigatório")
    Long produtoId,

    @@NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    Integer nota,

    @Size(max = 1000,
          message = "O comentário deve ter até 1000 caracteres")
    String comentario
){
}
