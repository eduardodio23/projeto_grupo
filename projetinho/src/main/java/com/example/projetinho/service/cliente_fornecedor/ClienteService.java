package com.example.projetinho.service.cliente_fornecedor;

import com.example.projetinho.dto.cliente_fornecedor.ClienteRequestDTO;
import com.example.projetinho.dto.cliente_fornecedor.ClienteResponseDTO;
import com.example.projetinho.entity.cliente_fornecedor.ClienteEntity;
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
                .map(clienteEntity -> new ClienteResponseDTO(
                        clienteEntity.getNome(),
                        clienteEntity.getEmail(),
                        clienteEntity.getTelefone()))
                .toList();
    }

    public ClienteEntity salvar(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cliente já cadastrado!");
        }
        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());

        return repository.save(novoCliente);
    }

    public ClienteEntity atualizar(Long id, ClienteRequestDTO dto) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("Cliente não cadastrado!");

        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());

        return repository.save(novoCliente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("Cliene não encontrado!");

        repository.deleteById(id);

    }

}