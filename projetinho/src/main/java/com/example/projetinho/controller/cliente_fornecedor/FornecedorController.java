package com.example.projetinho.controller.cliente_fornecedor;

import com.example.projetinho.dto.cliente_fornecedor.FornecedorRequestDTO;
import com.example.projetinho.dto.cliente_fornecedor.FornecedorResponseDTO;
import com.example.projetinho.service.cliente_fornecedor.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok()
                .body(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<?, ?>> salvar(
            @Valid @RequestBody FornecedorRequestDTO dto) {

        service.salvar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem",
                        "Fornecedor salvo com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<?, ?>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FornecedorRequestDTO dto) {

        service.atualizar(id, dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "mensagem",
                        "Fornecedor atualizado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<?, ?>> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "mensagem",
                        "Fornecedor removido com sucesso!"));
    }
}