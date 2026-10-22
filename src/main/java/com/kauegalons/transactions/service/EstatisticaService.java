package com.kauegalons.transactions.service;

import com.kauegalons.transactions.config.EstatisticaConfig;
import com.kauegalons.transactions.dto.EstatisticaDTO;
import com.kauegalons.transactions.model.Transacao;
import com.kauegalons.transactions.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;


@Slf4j
@Service
public class EstatisticaService {

    private final TransacaoRepository transacaoRepository;

    private final EstatisticaConfig estatisticaConfig;

    public EstatisticaService(TransacaoRepository transacaoRepository, EstatisticaConfig estatisticaConfig) {
        this.transacaoRepository = transacaoRepository;
        this.estatisticaConfig = estatisticaConfig;
    }

    public EstatisticaDTO getEstatisticas() {
        long inicio = System.nanoTime();

        OffsetDateTime limiteInicial = OffsetDateTime.now().minusSeconds(estatisticaConfig.segundos());

        List<Transacao> transacoesNaJanela = transacaoRepository.getListaTransacoes().stream()
                .filter(transacao -> !transacao.getDataHora().isBefore(limiteInicial))
                .toList();

        if (transacoesNaJanela.isEmpty()) {
            log.debug("Nenhuma transação nos últimos {} segundos. Retornando estatísticas zeradas.", estatisticaConfig.segundos());
            return new EstatisticaDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal max = transacoesNaJanela.stream()
                .map(Transacao::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal min = transacoesNaJanela.stream()
                .map(Transacao::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal sum = transacoesNaJanela.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = sum.divide(BigDecimal.valueOf(transacoesNaJanela.size()), 3, RoundingMode.HALF_EVEN);

        long duracaoMicros = (System.nanoTime() - inicio) / 1_000;
        log.debug("Estatísticas calculadas para {} transações em {} microssegundos.", transacoesNaJanela.size(), duracaoMicros);

        return new EstatisticaDTO(transacoesNaJanela.size(), sum, avg, min, max);
    }
}
