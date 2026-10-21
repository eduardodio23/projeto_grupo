package com.example.projetinho.dto.categoria_produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(min = 2, max = 100,
            message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 3, max = 500,
            message = "A descrição deve ter entre 3 e 500 caracteres.")
    private String descricao;
}