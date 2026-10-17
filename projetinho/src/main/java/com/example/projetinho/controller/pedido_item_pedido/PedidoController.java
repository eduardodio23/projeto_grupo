package com.example.projetinho.controller.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.PedidoRequestDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoResponseDTO;
import com.example.projetinho.service.pedido_item_pedido.PedidoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // Criar um pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(
            @RequestBody PedidoRequestDTO dto) {

        PedidoResponseDTO pedido = pedidoService.criar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedido);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(
                pedidoService.listarTodos()
        );
    }
}