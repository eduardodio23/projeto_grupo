package com.example.projetinho.controller.cliente_fornecedor;

import com.example.projetinho.dto.cliente_fornecedor.ClienteRequestDTO;
import com.example.projetinho.dto.cliente_fornecedor.ClienteResponseDTO;
import com.example.projetinho.service.cliente_fornecedor.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/Clientes", "/clientes"})
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok()
                .body(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<?,?>> salvar(
            @Valid
            @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO cliente = service.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Cliente salvo com sucesso!",
                        "id", cliente.getId()));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<?,?>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("mensagem", "Cliente atualizado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<?,?>> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("mensagem", "Cliente removido com sucesso!"));
    }
}
