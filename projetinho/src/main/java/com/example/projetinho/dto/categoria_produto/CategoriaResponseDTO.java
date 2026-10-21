package com.example.projetinho.dto.categoria_produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
}