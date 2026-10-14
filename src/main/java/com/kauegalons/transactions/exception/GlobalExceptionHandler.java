package com.kauegalons.transactions.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> tratarJsonInvalido(HttpMessageNotReadableException e) {
        log.warn("Requisição com JSON inválido (400): {}", e.getMostSpecificCause().getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> tratarErroInesperado(Exception e) {
        if (e instanceof ErrorResponse errorResponse) {
            log.warn("Requisição inválida ({}): {}", errorResponse.getStatusCode().value(), e.getMessage());
            return ResponseEntity.status(errorResponse.getStatusCode()).build();
        }

        log.error("Erro inesperado ao processar a requisição.", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
