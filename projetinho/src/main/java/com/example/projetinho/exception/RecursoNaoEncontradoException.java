package com.example.projetinho.exception;

// Lançada quando um registro buscado pelo ID não existe (HTTP 404)
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
