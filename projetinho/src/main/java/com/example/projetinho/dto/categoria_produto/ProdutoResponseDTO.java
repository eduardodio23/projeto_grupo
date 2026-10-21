package com.example.projetinho.dto.categoria_produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoResponseDTO {

    private Long id;
    private String produto;
    private Integer quantidade;
    private BigDecimal preco;
    private String categoria;
}
