package com.example.projetinho.service.pedido_item_pedido;

import com.example.projetinho.dto.pedido_item_pedido.ItemPedidoDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoRequestDTO;
import com.example.projetinho.dto.pedido_item_pedido.PedidoResponseDTO;

import com.example.projetinho.entity.cliente_fornecedor.ClienteEntity;
import com.example.projetinho.entity.categoria_produto.ProdutoEntity;
import com.example.projetinho.entity.pedido_item_pedido.ItemPedidoEntity;
import com.example.projetinho.entity.pedido_item_pedido.PedidoEntity;
import com.example.projetinho.exception.RecursoNaoEncontradoException;

import com.example.projetinho.repository.cliente_fornecedor.ClienteRepository;
import com.example.projetinho.repository.categoria_produto.ProdutoRepository;
import com.example.projetinho.repository.pedido_item_pedido.PedidoRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    private static final Set<String> STATUS_VALIDOS = Set.of(
            "PENDENTE", "PAGO", "ENVIADO", "ENTREGUE", "CANCELADO");

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado");
        }

        return pedidoRepository.findByCliente_Id(clienteId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPorStatus(String status) {
        return pedidoRepository.findByStatus(validarStatus(status))
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, String status) {
        PedidoEntity pedido = buscarEntidade(id);
        pedido.setStatus(validarStatus(status));

        return converterParaResponse(pedidoRepository.save(pedido));
    }

    // Os itens são removidos junto (cascade + orphanRemoval)
    @Transactional
    public void excluir(Long id) {
        pedidoRepository.delete(buscarEntidade(id));
    }

    private String validarStatus(String status) {
        String normalizado = status == null ? "" : status.trim().toUpperCase();

        if (!STATUS_VALIDOS.contains(normalizado)) {
            throw new IllegalArgumentException(
                    "Status inválido. Use: " + STATUS_VALIDOS);
        }

        return normalizado;
    }

    private PedidoEntity buscarEntidade(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Pedido não encontrado"));
    }

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {

        ClienteEntity cliente = clienteRepository
                .findById(dto.getClienteId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Cliente não encontrado"));

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
                            new RecursoNaoEncontradoException("Produto não encontrado"));

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

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {

        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Pedido não encontrado"));

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