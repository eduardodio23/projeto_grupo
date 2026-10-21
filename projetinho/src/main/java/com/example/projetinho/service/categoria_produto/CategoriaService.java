package com.example.projetinho.service.categoria_produto;

import com.example.projetinho.dto.categoria_produto.CategoriaRequestDTO;
import com.example.projetinho.dto.categoria_produto.CategoriaResponseDTO;
import com.example.projetinho.entity.categoria_produto.CategoriaEntity;
import com.example.projetinho.exception.CategoriaException;
import com.example.projetinho.exception.RecursoNaoEncontradoException;
import com.example.projetinho.repository.categoria_produto.CategoriaRepository;

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
        return converterParaDTO(buscarEntidade(id));
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {

        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new CategoriaException(
                    "Já existe uma categoria com esse nome."
            );
        }

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        CategoriaEntity salva = categoriaRepository.save(categoria);

        return converterParaDTO(salva);
    }

    public CategoriaResponseDTO atualizar(
            Long id,
            CategoriaRequestDTO dto) {

        CategoriaEntity categoria = buscarEntidade(id);

        boolean nomeExiste = categoriaRepository.existsByNome(dto.getNome());

        if (nomeExiste && !categoria.getNome().equalsIgnoreCase(dto.getNome())) {
            throw new CategoriaException(
                    "Já existe outra categoria com esse nome."
            );
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        CategoriaEntity atualizada = categoriaRepository.save(categoria);

        return converterParaDTO(atualizada);
    }

    public void deletar(Long id) {
        categoriaRepository.delete(buscarEntidade(id));
    }

    private CategoriaEntity buscarEntidade(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada: " + id
                        )
                );
    }

    private CategoriaResponseDTO converterParaDTO(CategoriaEntity categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}
