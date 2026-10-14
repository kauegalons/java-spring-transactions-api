package com.kauegalons.transactions.controller;

import com.kauegalons.transactions.dto.TransacaoDTO;
import com.kauegalons.transactions.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {

        try {
            transacaoService.criarTransacao(transacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTransacao() {
        transacaoService.apagarTodasTransacoes();
        return ResponseEntity.ok().build();
    }

}
