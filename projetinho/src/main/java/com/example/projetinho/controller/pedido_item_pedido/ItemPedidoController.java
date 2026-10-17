package com.example.projetinho.controller.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.ItemPedidoDTO;
import com.example.projetinho.service.pedido_item_pedido.ItemPedidoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    // Adicionar item a um pedido existente
    @PostMapping("/pedido/{pedidoId}")
    public ResponseEntity<ItemPedidoDTO> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedidoDTO dto) {

        ItemPedidoDTO item = itemPedidoService.adicionarItem(
                pedidoId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(item);
    }

    // Buscar item por ID
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                itemPedidoService.buscarPorId(id)
        );
    }

    // Listar itens de um pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedidoDTO>> listarPorPedido(
            @PathVariable Long pedidoId) {

        return ResponseEntity.ok(
                itemPedidoService.listarPorPedido(pedidoId)
        );
    }

    // Excluir item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        itemPedidoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}