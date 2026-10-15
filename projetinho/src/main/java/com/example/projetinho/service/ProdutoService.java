package com.example.projetinho.service;

import com.example.projetinho.dto.ProdutoRequestDTO;
import com.example.projetinho.dto.ProdutoResponseDTO;
import com.example.projetinho.entity.Categoria;
import com.example.projetinho.entity.Produto;
import com.example.projetinho.exception.ProdutoException;
import com.example.projetinho.repository.ProdutoRepository;
import com.example.projetinho.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Produto não encontrado: " + id
                        )
                );

        return converterParaDTO(produto);
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ProdutoException(
                                "Categoria não encontrada: "
                                        + dto.getCategoriaId()
                        )
                );

        Produto produto = new Produto();
        produto.setNome(dto.getProduto());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        Produto salvo = produtoRepository.save(produto);

        return converterParaDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(
            Long id,
            ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Produto não encontrado: " + id
                        )
                );

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ProdutoException(
                                "Categoria não encontrada: "
                                        + dto.getCategoriaId()
                        )
                );

        produto.setNome(dto.getProduto());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        Produto atualizado = produtoRepository.save(produto);

        return converterParaDTO(atualizado);
    }


    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Produto não encontrado: " + id
                        )
                );

        produtoRepository.delete(produto);
    }


    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getPreco(),
                produto.getCategoria().getNome()
        );
    }
}