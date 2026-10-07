package com.kauegalons.transactions.controller;

import com.kauegalons.transactions.dto.TransacaoDTO;
import com.kauegalons.transactions.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {

        transacaoService.validarTransacao(transacaoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
