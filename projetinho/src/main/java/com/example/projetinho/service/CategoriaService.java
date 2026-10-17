package com.example.projetinho.service;

import com.example.projetinho.dto.CategoriaRequestDTO;
import com.example.projetinho.dto.CategoriaResponseDTO;
import com.example.projetinho.entity.Categoria;
import com.example.projetinho.exception.ProdutoException;
import com.example.projetinho.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Categoria não encontrada: " + id
                        )
                );

        return converterParaDTO(categoria);
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {

        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new ProdutoException(
                    "Já existe uma categoria com esse nome."
            );
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria salva = categoriaRepository.save(categoria);

        return converterParaDTO(salva);
    }

    public CategoriaResponseDTO atualizar(
            Long id,
            CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Categoria não encontrada: " + id
                        )
                );

        boolean nomeExiste = categoriaRepository.existsByNome(dto.getNome());

        if (nomeExiste && !categoria.getNome().equalsIgnoreCase(dto.getNome())) {
            throw new ProdutoException(
                    "Já existe outra categoria com esse nome."
            );
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria atualizada = categoriaRepository.save(categoria);

        return converterParaDTO(atualizada);
    }

    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoException(
                                "Categoria não encontrada: " + id
                        )
                );

        categoriaRepository.delete(categoria);
    }


    private CategoriaResponseDTO converterParaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}