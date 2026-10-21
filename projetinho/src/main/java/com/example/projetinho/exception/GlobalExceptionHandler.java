package com.example.projetinho.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

// Único tratador de exceções da aplicação (antes havia três tratando a mesma exceção)
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarNaoEncontrado(
            RecursoNaoEncontradoException erro) {

        return resposta(HttpStatus.NOT_FOUND, erro.getMessage());
    }

    @ExceptionHandler({
            ProdutoException.class,
            CategoriaException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> tratarRegraDeNegocio(
            RuntimeException erro) {

        return resposta(HttpStatus.BAD_REQUEST, erro.getMessage());
    }

    // Ex.: excluir uma categoria que ainda possui produtos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarIntegridade(
            DataIntegrityViolationException erro) {

        return resposta(HttpStatus.CONFLICT,
                "Operação não permitida: o registro está vinculado a outros dados.");
    }

    // JSON mal formatado no corpo da requisição (ex.: vírgula sobrando)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarJsonInvalido(
            HttpMessageNotReadableException erro) {

        return resposta(HttpStatus.BAD_REQUEST,
                "JSON inválido no corpo da requisição. Verifique vírgulas, aspas e chaves.");
    }

    // Corpo enviado como Text/XML em vez de JSON
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> tratarTipoDeConteudo(
            HttpMediaTypeNotSupportedException erro) {

        return resposta(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Envie o corpo como JSON (Content-Type: application/json).");
    }

    // URL que não existe (ex.: /itenspedido)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> tratarRotaInexistente(
            NoResourceFoundException erro) {

        return resposta(HttpStatus.NOT_FOUND,
                "Rota não encontrada: /" + erro.getResourcePath());
    }

    // Método HTTP não suportado na rota (ex.: POST onde só existe GET)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> tratarMetodoNaoPermitido(
            HttpRequestMethodNotSupportedException erro) {

        return resposta(HttpStatus.METHOD_NOT_ALLOWED,
                "Método " + erro.getMethod() + " não permitido nesta rota. "
                        + "Métodos aceitos: " + erro.getSupportedHttpMethods());
    }

    // Tipo errado na URL (ex.: /pedidos/abc em vez de /pedidos/1)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarTipoInvalido(
            MethodArgumentTypeMismatchException erro) {

        return resposta(HttpStatus.BAD_REQUEST,
                "Valor inválido para '" + erro.getName() + "': " + erro.getValue());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(
            MethodArgumentNotValidException erro) {

        String mensagem = erro.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(e -> e.getDefaultMessage())
                .orElse("Dados inválidos.");

        return resposta(HttpStatus.BAD_REQUEST, mensagem);
    }

    // Qualquer outro erro inesperado: registra no log e não expõe o stack trace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErroInesperado(
            Exception erro) {

        log.error("Erro inesperado", erro);

        return resposta(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor.");
    }

    // Map.of não aceita null, então mensagens vazias recebem um texto padrão
    private ResponseEntity<Map<String, String>> resposta(
            HttpStatus status, String mensagem) {

        return ResponseEntity
                .status(status)
                .body(Map.of("mensagem",
                        mensagem != null ? mensagem : status.getReasonPhrase()));
    }
}
