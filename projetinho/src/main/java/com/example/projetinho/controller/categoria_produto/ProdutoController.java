package com.example.projetinho.controller.categoria_produto;

import com.example.projetinho.dto.categoria_produto.ProdutoRequestDTO;
import com.example.projetinho.dto.categoria_produto.ProdutoResponseDTO;
import com.example.projetinho.service.categoria_produto.ProdutoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                produtoService.listarTodos()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                produtoService.buscarPorId(id)
        );
    }


    @PostMapping({"", "/salvar"})
    public ResponseEntity<ProdutoResponseDTO> salvar(
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO produto = produtoService.salvar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produto);
    }


    @PutMapping({"/{id}", "/atualizar/{id}"})
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        return ResponseEntity.ok(
                produtoService.atualizar(id, dto)
        );
    }

    @DeleteMapping({"/{id}", "/deletar/{id}"})
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}