package com.example.projetinho.service.avaliacao;

import com.example.projetinho.dto.avaliacao.AvaliacaoRequestDTO;
import com.example.projetinho.dto.avaliacao.AvaliacaoResponseDTO;
import com.example.projetinho.entity.avaliacao.AvaliacaoEntity;
import com.example.projetinho.entity.categoria_produto.ProdutoEntity;
import com.example.projetinho.entity.cliente_fornecedor.ClienteEntity;
import com.example.projetinho.exception.RecursoNaoEncontradoException;
import com.example.projetinho.repository.avaliacao.AvaliacaoRepository;
import com.example.projetinho.repository.categoria_produto.ProdutoRepository;
import com.example.projetinho.repository.cliente_fornecedor.ClienteRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public AvaliacaoResponseDTO cadastrar(AvaliacaoRequestDTO dto) {

        // Cada cliente pode avaliar um produto apenas uma vez
        if (avaliacaoRepository.existsByCliente_IdAndProduto_Id(
                dto.getClienteId(), dto.getProdutoId())) {
            throw new IllegalArgumentException(
                    "Este cliente já avaliou este produto.");
        }

        AvaliacaoEntity avaliacao = new AvaliacaoEntity();
        avaliacao.setCliente(buscarCliente(dto.getClienteId()));
        avaliacao.setProduto(buscarProduto(dto.getProdutoId()));
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return converterParaDTO(avaliacaoRepository.save(avaliacao));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarTodas() {
        return avaliacaoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponseDTO buscarPorId(Long id) {
        return converterParaDTO(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarPorProduto(Long produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new RecursoNaoEncontradoException(
                    "Produto não encontrado: " + produtoId);
        }

        return avaliacaoRepository.findByProduto_Id(produtoId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException(
                    "Cliente não encontrado: " + clienteId);
        }

        return avaliacaoRepository.findByCliente_Id(clienteId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // Permite alterar apenas a nota e o comentário
    @Transactional
    public AvaliacaoResponseDTO atualizar(Long id, AvaliacaoRequestDTO dto) {

        AvaliacaoEntity avaliacao = buscarEntidade(id);

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return converterParaDTO(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public void excluir(Long id) {
        avaliacaoRepository.delete(buscarEntidade(id));
    }

    private AvaliacaoEntity buscarEntidade(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Avaliação não encontrada: " + id));
    }

    private ClienteEntity buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente não encontrado: " + clienteId));
    }

    private ProdutoEntity buscarProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado: " + produtoId));
    }

    private AvaliacaoResponseDTO converterParaDTO(AvaliacaoEntity avaliacao) {
        return new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getCliente().getId(),
                avaliacao.getCliente().getNome(),
                avaliacao.getProduto().getId(),
                avaliacao.getProduto().getNome(),
                avaliacao.getNota(),
                avaliacao.getComentario()
        );
    }
}
