package com.kauegalons.transactions.service;

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

    public EstatisticaService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public EstatisticaDTO getEstatisticas() {
        long inicio = System.nanoTime();

        OffsetDateTime ultimoMinuto = OffsetDateTime.now().minusSeconds(60);

        List<Transacao> transacoesUltimoMinuto = transacaoRepository.getListaTransacoes().stream()
                .filter(transacao -> !transacao.getDataHora().isBefore(ultimoMinuto))
                .toList();

        if (transacoesUltimoMinuto.isEmpty()) {
            log.debug("Nenhuma transação nos últimos 60 segundos. Retornando estatísticas zeradas.");
            return new EstatisticaDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal max = transacoesUltimoMinuto.stream()
                .map(Transacao::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal min = transacoesUltimoMinuto.stream()
                .map(Transacao::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal sum = transacoesUltimoMinuto.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = sum.divide(BigDecimal.valueOf(transacoesUltimoMinuto.size()), 3, RoundingMode.HALF_EVEN);

        long duracaoMicros = (System.nanoTime() - inicio) / 1_000;
        log.debug("Estatísticas calculadas para {} transações em {} microssegundos.", transacoesUltimoMinuto.size(), duracaoMicros);

        return new EstatisticaDTO(transacoesUltimoMinuto.size(), sum, avg, min, max);
    }
}
