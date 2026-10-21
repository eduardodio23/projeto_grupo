package com.example.projetinho.dto.avaliacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoResponseDTO {
    private Long id;
    private Long clienteId;
    private String cliente;
    private Long produtoId;
    private String produto;
    private Integer nota;
    private String comentario;
}
