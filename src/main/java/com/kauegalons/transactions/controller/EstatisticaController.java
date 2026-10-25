package com.kauegalons.transactions.controller;

import com.kauegalons.transactions.docs.EstatisticaControllerDoc;
import com.kauegalons.transactions.dto.EstatisticaDTO;
import com.kauegalons.transactions.service.EstatisticaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController implements EstatisticaControllerDoc {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstatisticaDTO> getEstatistica() {
        log.debug("Requisição recebida para obter estatísticas.");
        EstatisticaDTO estatisticas = estatisticaService.getEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}
