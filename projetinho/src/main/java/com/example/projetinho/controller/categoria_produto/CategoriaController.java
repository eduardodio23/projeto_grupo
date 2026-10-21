package com.example.projetinho.controller.categoria_produto;

import com.example.projetinho.dto.categoria_produto.CategoriaRequestDTO;
import com.example.projetinho.dto.categoria_produto.CategoriaResponseDTO;
import com.example.projetinho.service.categoria_produto.CategoriaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(
                categoriaService.listarTodas()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoriaService.buscarPorId(id)
        );
    }


    @PostMapping({"", "/salvar"})
    public ResponseEntity<CategoriaResponseDTO> salvar(
            @Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO categoria = categoriaService.salvar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoria);
    }

    @PutMapping({"/{id}", "/atualizar/{id}"})
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {

        return ResponseEntity.ok(
                categoriaService.atualizar(id, dto)
        );
    }


    @DeleteMapping({"/{id}", "/deletar/{id}"})
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        categoriaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}