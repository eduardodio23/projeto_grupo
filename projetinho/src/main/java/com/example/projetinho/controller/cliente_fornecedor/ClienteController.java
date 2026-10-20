package com.example.projetinho.controller.cliente_fornecedor;

@RestController
@RequestMapping("Clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok()
                .body(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<?,?>> salvar(
            @Valid
            @RequestBody ClienteRequestDTO dto) {
        service.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Cliente salvo com sucesso!"));

    }

    @PutMapping
    public ResponseEntity<Map<?,?>> atualizar(
            @PathVariable Long Id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        service.atualizar(Id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("mensagem", "Cliente atualizado com sucesso!"));
    }

    @DeleteMapping
    public ResponseEntity<Map<?,?>> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("mensagem", "Cliente removido com sucesso!"));
    }
}