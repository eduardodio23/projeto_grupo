package com.example.projetinho.service.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.ItemPedidoDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoRequestDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoResponseDTO;

import com.example.projetinho.entity.ClienteEntity;
import com.example.projetinho.entity.ProdutoEntity;
import com.example.projetinho.entity.pedido_item_pedido.ItemPedidoEntity;
import com.example.projetinho.entity.pedido_item_pedido.PedidoEntity;

import com.example.projetinho.repository.ClienteRepository;
import com.example.projetinho.repository.ProdutoRepository;
import com.example.projetinho.repository.pedido_item_pedido.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {

        ClienteEntity cliente = clienteRepository
                .findById(dto.getClienteId())
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "O pedido precisa ter pelo menos um item");
        }

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());
        pedido.setStatus("PENDENTE");
        pedido.setTotal(BigDecimal.ZERO);

        for (ItemPedidoDTO itemDTO : dto.getItens()) {

            if (itemDTO.getQuantidade() == null
                    || itemDTO.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero");
            }

            ProdutoEntity produto = produtoRepository
                    .findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new RuntimeException("Produto não encontrado"));

            ItemPedidoEntity item = new ItemPedidoEntity();

            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());

            // Busca o preço cadastrado no produto
            item.setPrecoUnitario(produto.getPreco());

            // Adiciona o item e associa ao pedido
            pedido.adicionarItem(item);
        }

        pedido.calcularTotal();

        PedidoEntity salvo = pedidoRepository.save(pedido);

        return converterParaResponse(salvo);
    }

    @Transactional
    public PedidoResponseDTO buscarPorId(Long id) {

        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        return converterParaResponse(pedido);
    }

    private PedidoResponseDTO converterParaResponse(
            PedidoEntity pedido) {

        List<ItemPedidoDTO> itensDTO = pedido.getItens()
                .stream()
                .map(item -> new ItemPedidoDTO(
                        item.getId(),
                        pedido.getId(),
                        item.getProduto().getId(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                ))
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getCliente().getId(),
                pedido.getTotal(),
                itensDTO
        );
    }
}