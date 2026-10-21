package com.example.projetinho.service.cliente_fornecedor;

import com.example.projetinho.dto.cliente_fornecedor.ClienteRequestDTO;
import com.example.projetinho.dto.cliente_fornecedor.ClienteResponseDTO;
import com.example.projetinho.entity.cliente_fornecedor.ClienteEntity;
import com.example.projetinho.exception.RecursoNaoEncontradoException;
import com.example.projetinho.repository.cliente_fornecedor.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<ClienteResponseDTO> listarTodos() {
        return repository
                .findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return converterParaDTO(buscarEntidade(id));
    }

    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cliente já cadastrado!");
        }
        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());

        return converterParaDTO(repository.save(novoCliente));
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        // Busca o cliente existente (antes era criado um cliente novo)
        ClienteEntity cliente = buscarEntidade(id);

        if (!cliente.getEmail().equals(dto.getEmail())
                && repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "Email já cadastrado para outro cliente!");
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        return converterParaDTO(repository.save(cliente));
    }

    public void excluir(Long id) {
        if (!repository.existsById(id))
            throw new RecursoNaoEncontradoException("Cliente não encontrado!");

        repository.deleteById(id);

    }

    private ClienteEntity buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Cliente não encontrado!"));
    }

    private ClienteResponseDTO converterParaDTO(ClienteEntity cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone());
    }

}
