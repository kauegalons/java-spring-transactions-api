package com.kauegalons.transactions.controller;

import com.kauegalons.transactions.dto.EstatisticaDTO;
import com.kauegalons.transactions.service.EstatisticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<EstatisticaDTO> getEstatistica() {
        EstatisticaDTO estatisticas = estatisticaService.getEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}
