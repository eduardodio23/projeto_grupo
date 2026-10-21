package com.example.projetinho.controller.avaliacao;

import com.example.projetinho.dto.avaliacao.AvaliacaoRequestDTO;
import com.example.projetinho.dto.avaliacao.AvaliacaoResponseDTO;
import com.example.projetinho.service.avaliacao.AvaliacaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    // Cadastrar uma avaliação
    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> cadastrar(
            @Valid @RequestBody AvaliacaoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(avaliacaoService.cadastrar(dto));
    }

    // Listar todas as avaliações
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(avaliacaoService.listarTodas());
    }

    // Buscar avaliação por ID
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(avaliacaoService.buscarPorId(id));
    }

    // Listar avaliações de um produto
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorProduto(
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                avaliacaoService.listarPorProduto(produtoId));
    }

    // Listar avaliações feitas por um cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId) {

        return ResponseEntity.ok(
                avaliacaoService.listarPorCliente(clienteId));
    }

    // Atualizar nota e comentário
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO dto) {

        return ResponseEntity.ok(avaliacaoService.atualizar(id, dto));
    }

    // Excluir avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avaliacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
