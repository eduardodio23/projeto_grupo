package com.example.projetinho.dto.cliente_fornecedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteResponseDTO {
    private String nome;
    private String email;
    private String telefone;
}