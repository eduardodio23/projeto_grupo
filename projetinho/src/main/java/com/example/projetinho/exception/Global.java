package com.example.projetinho.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class Global {

    @ExceptionHandler(ProdutoException.class)
    public ResponseEntity<Map<String, String>> tratarProdutoException(
            ProdutoException erro) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", erro.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarIllegalArgumentException(
            IllegalArgumentException erro) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", erro.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(
            MethodArgumentNotValidException erro) {

        String mensagem = Objects.requireNonNull(
                erro.getFieldErrors().getFirst().getDefaultMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", mensagem));
    }
}