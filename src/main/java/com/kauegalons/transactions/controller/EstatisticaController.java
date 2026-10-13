package com.kauegalons.transactions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    @GetMapping
    public ResponseEntity<String> getEstatistica() {
        return ResponseEntity.ok("Estatística de transações");
    }
}
