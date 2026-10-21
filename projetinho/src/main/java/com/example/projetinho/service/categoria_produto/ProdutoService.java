package com.example.projetinho.service.categoria_produto;

import com.example.projetinho.dto.categoria_produto.ProdutoRequestDTO;
import com.example.projetinho.dto.categoria_produto.ProdutoResponseDTO;
import com.example.projetinho.entity.categoria_produto.CategoriaEntity;
import com.example.projetinho.entity.categoria_produto.ProdutoEntity;
import com.example.projetinho.exception.RecursoNaoEncontradoException;
import com.example.projetinho.repository.categoria_produto.CategoriaRepository;
import com.example.projetinho.repository.categoria_produto.ProdutoRepository;

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
        return converterParaDTO(buscarEntidade(id));
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        CategoriaEntity categoria = buscarCategoria(dto.getCategoriaId());

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(dto.getProduto());
        produtoEntity.setQuantidade(dto.getQuantidade());
        produtoEntity.setPreco(dto.getPreco());
        produtoEntity.setCategoria(categoria);

        ProdutoEntity salvo = produtoRepository.save(produtoEntity);

        return converterParaDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(
            Long id,
            ProdutoRequestDTO dto) {

        ProdutoEntity produtoEntity = buscarEntidade(id);
        CategoriaEntity categoria = buscarCategoria(dto.getCategoriaId());

        produtoEntity.setNome(dto.getProduto());
        produtoEntity.setQuantidade(dto.getQuantidade());
        produtoEntity.setPreco(dto.getPreco());
        produtoEntity.setCategoria(categoria);

        ProdutoEntity atualizado = produtoRepository.save(produtoEntity);

        return converterParaDTO(atualizado);
    }


    public void deletar(Long id) {
        produtoRepository.delete(buscarEntidade(id));
    }

    private ProdutoEntity buscarEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Produto não encontrado: " + id
                        )
                );
    }

    private CategoriaEntity buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada: " + categoriaId
                        )
                );
    }

    private ProdutoResponseDTO converterParaDTO(ProdutoEntity produtoEntity) {
        return new ProdutoResponseDTO(
                produtoEntity.getId(),
                produtoEntity.getNome(),
                produtoEntity.getQuantidade(),
                produtoEntity.getPreco(),
                produtoEntity.getCategoria().getNome()
        );
    }
}
