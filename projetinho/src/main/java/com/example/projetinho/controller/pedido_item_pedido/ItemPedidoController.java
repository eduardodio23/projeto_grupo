package com.example.projetinho.controller.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.ItemPedidoDTO;
import com.example.projetinho.service.pedido_item_pedido.ItemPedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Rotas antigas mantidas para não quebrar quem já usa
@RequestMapping({"/item_pedidos", "/itenspedidos", "/itens-pedido"})
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    // Adicionar item informando o pedidoId no corpo da requisição
    @PostMapping
    public ResponseEntity<ItemPedidoDTO> adicionarItem(
            @Valid @RequestBody ItemPedidoDTO dto) {

        if (dto.getPedidoId() == null) {
            throw new IllegalArgumentException(
                    "O ID do pedido é obrigatório.");
        }

        return adicionarItem(dto.getPedidoId(), dto);
    }

    // Adicionar item a um pedido existente
    @PostMapping("/pedido/{pedidoId}")
    public ResponseEntity<ItemPedidoDTO> adicionarItem(
            @PathVariable Long pedidoId,
            @Valid @RequestBody ItemPedidoDTO dto) {

        ItemPedidoDTO item = itemPedidoService.adicionarItem(
                pedidoId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(item);
    }

    // Listar todos os itens de todos os pedidos
    @GetMapping
    public ResponseEntity<List<ItemPedidoDTO>> listarTodos() {

        return ResponseEntity.ok(
                itemPedidoService.listarTodos()
        );
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

    // Alterar a quantidade de um item (aceita PUT e PATCH)
    @RequestMapping(
            value = "/{id}",
            method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<ItemPedidoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ItemPedidoDTO dto) {

        // Só a quantidade é usada; ela é validada no service
        return ResponseEntity.ok(
                itemPedidoService.atualizarQuantidade(id, dto.getQuantidade())
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