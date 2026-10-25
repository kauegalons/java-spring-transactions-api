package com.kauegalons.transactions.controller;

import com.kauegalons.transactions.docs.TransacaoControllerDoc;
import com.kauegalons.transactions.dto.TransacaoDTO;
import com.kauegalons.transactions.service.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping ("/transacao")
public class TransacaoController implements TransacaoControllerDoc {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {

        try {
            transacaoService.criarTransacao(transacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(IllegalArgumentException e){
            log.warn("Transação rejeitada (422): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deletarTransacao() {
        transacaoService.apagarTodasTransacoes();
        return ResponseEntity.ok().build();
    }

}
