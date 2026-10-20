package com.example.projetinho.dto.cliente_fornecedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteRequestDTO {

    @NotBlank(message = "O que é obrigatório.")
    @Size(min = 3, message = "O nomme deve ter no minimo 3 caracteres.") @NotNull(message = "O que é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Size(max = 100  , message = "O email deve ter no máximo 100 caracteres.")
    private String email;

    @NotBlank(message = "Telefone é obrigatório.")
    @Size(min = 11, max = 21, message = "O telefone deve ter ente 11 e 21  caracteres.") @NotNull(message = "O que é obrigatório.")
    private String telefone;

}