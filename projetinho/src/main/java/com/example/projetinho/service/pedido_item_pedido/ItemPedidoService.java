package com.example.projetinho.service.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.ItemPedidoDTO;
import com.example.projetinho.entity.ProdutoEntity;
import com.example.projetinho.entity.pedido_item_pedido.ItemPedidoEntity;
import com.example.projetinho.entity.pedido_item_pedido.PedidoEntity;
import com.example.projetinho.repository.ProdutoRepository;
import com.example.projetinho.repository.pedido_item_pedido.ItemPedidoRepository;
import com.example.projetinho.repository.pedido_item_pedido.PedidoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    // Cadastrar um item em um pedido existente
    @Transactional
    public ItemPedidoDTO adicionarItem(
            Long pedidoId,
            ItemPedidoDTO dto) {

        PedidoEntity pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        ProdutoEntity produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        if (dto.getQuantidade() == null
                || dto.getQuantidade() <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero");
        }

        ItemPedidoEntity item = new ItemPedidoEntity();

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());

        // Usa o preço cadastrado no produto
        item.setPrecoUnitario(produto.getPreco());

        // Atualiza a lista de itens do pedido
        pedido.adicionarItem(item);
        pedido.calcularTotal();

        // O cascade do PedidoEntity persiste o item
        pedidoRepository.save(pedido);

        return converterParaDTO(item);
    }

    // Buscar um item pelo ID
    @Transactional(readOnly = true)
    public ItemPedidoDTO buscarPorId(Long id) {

        ItemPedidoEntity item = itemPedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item não encontrado"));

        return converterParaDTO(item);
    }

    // Listar todos os itens de um pedido
    @Transactional(readOnly = true)
    public List<ItemPedidoDTO> listarPorPedido(Long pedidoId) {

        if (!pedidoRepository.existsById(pedidoId)) {
            throw new RuntimeException("Pedido não encontrado");
        }

        return itemPedidoRepository
                .findByPedido_Id(pedidoId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // Excluir um item e recalcular o total do pedido
    @Transactional
    public void excluir(Long id) {

        ItemPedidoEntity item = itemPedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item não encontrado"));

        PedidoEntity pedido = item.getPedido();

        pedido.getItens().remove(item);
        pedido.calcularTotal();

        itemPedidoRepository.delete(item);
        pedidoRepository.save(pedido);
    }

    // Converter entidade em DTO
    private ItemPedidoDTO converterParaDTO(
            ItemPedidoEntity item) {

        return new ItemPedidoDTO(
                item.getId(),
                item.getPedido().getId(),
                item.getProduto().getId(),
                item.getQuantidade(),
                item.getPrecoUnitario()
        );
    }
}
