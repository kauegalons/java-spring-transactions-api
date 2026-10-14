package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.EstatisticaDTO;
import com.kauegalons.transactions.model.Transacao;
import com.kauegalons.transactions.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;


@Service
public class EstatisticaService {

    private final TransacaoRepository transacaoRepository;

    public EstatisticaService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public EstatisticaDTO getEstatisticas() {

        OffsetDateTime ultimoMinuto = OffsetDateTime.now().minusSeconds(60);

        List<Transacao> transacoesUltimoMinuto = transacaoRepository.getListaTransacoes().stream()
                .filter(transacao -> transacao.getDataHora().isAfter(ultimoMinuto))
                .toList();

        if (transacoesUltimoMinuto.isEmpty()) {
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

        return new EstatisticaDTO(transacoesUltimoMinuto.size(), sum, avg, min, max);
    }
}
