package com.example.projetinho.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2,max = 100, message = "O produto deve ter entre 2 e 100 caracteres.")
    private String produto;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "O mínimo é 1 item.")
    private Integer quantidade;

    @NotNull(message = "O preço é obrigatório.")
    @Min(value = 0, message = "O preço não pode ser negativo.")
    private BigDecimal preco;

    @NotNull(message = "A categoria é obrigatória.")
    @Min(value = 1, message = "O ID da categoria deve ser positivo.")
    private Long categoriaId;
}