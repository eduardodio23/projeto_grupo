package com.example.projetinho.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ClienteGlobalException {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<?, ?>> handleIllegalArgumentException(IllegalArgumentException erro) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body((Map.of("mensagem", erro.getMessage())));
    }
}