package com.example.projetinho.controller.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.PedidoRequestDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoResponseDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoStatusDTO;
import com.example.projetinho.service.pedido_item_pedido.PedidoService;

import jakarta.validation.Valid;
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
            @Valid @RequestBody PedidoRequestDTO dto) {

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

    // Listar pedidos de um cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId) {

        return ResponseEntity.ok(
                pedidoService.listarPorCliente(clienteId)
        );
    }

    // Listar pedidos por status (ex.: /pedidos/status/PENDENTE)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                pedidoService.listarPorStatus(status)
        );
    }

    // Alterar o status do pedido (aceita PUT e PATCH)
    @RequestMapping(
            value = {"/{id}", "/{id}/status"},
            method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody PedidoStatusDTO dto) {

        return ResponseEntity.ok(
                pedidoService.atualizarStatus(id, dto.getStatus())
        );
    }

    // Excluir pedido (e seus itens)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        pedidoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}