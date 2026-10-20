package com.example.projetinho.service.cliente_fornecedor;

import com.example.projetinho.dto.cliente_fornecedor.FornecedorRequestDTO;
import com.example.projetinho.dto.cliente_fornecedor.FornecedorResponseDTO;
import com.example.projetinho.entity.cliente_fornecedor.FornecedorEntity;
import com.example.projetinho.repository.cliente_fornecedor.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    public List<FornecedorResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(fornecedor -> new FornecedorResponseDTO(
                        fornecedor.getNome(),
                        fornecedor.getEmail(),
                        fornecedor.getTelefone()))
                .toList();
    }

    public FornecedorEntity salvar(FornecedorRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "Fornecedor já cadastrado!");
        }

        FornecedorEntity novoFornecedor = new FornecedorEntity();

        novoFornecedor.setNome(dto.getNome());
        novoFornecedor.setEmail(dto.getEmail());
        novoFornecedor.setTelefone(dto.getTelefone());

        return repository.save(novoFornecedor);
    }

    public FornecedorEntity atualizar(
            Long id, FornecedorRequestDTO dto) {

        FornecedorEntity fornecedor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Fornecedor não encontrado!"));

        if (!fornecedor.getEmail().equals(dto.getEmail())
                && repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "Email já cadastrado para outro fornecedor!");
        }

        fornecedor.setNome(dto.getNome());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());

        return repository.save(fornecedor);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Fornecedor não encontrado!");
        }

        repository.deleteById(id);
    }
}