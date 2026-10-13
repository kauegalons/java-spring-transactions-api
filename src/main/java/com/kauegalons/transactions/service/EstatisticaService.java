package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.TransacaoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class EstatisticaService {

    public BigDecimal getMax(List<TransacaoDTO> transacoes) {
        return transacoes.stream()
                .map(TransacaoDTO::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getMin(List<TransacaoDTO> transacoes) {
        return transacoes.stream()
                .map(TransacaoDTO::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public long getCount(List<TransacaoDTO> transacoes) {
        return transacoes.size();
    }

    public BigDecimal getSum(List<TransacaoDTO> transacoes) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (TransacaoDTO transacaoDTO : transacoes){
            if( transacaoDTO.getValor() != null){
                valorTotal = valorTotal.add(transacaoDTO.getValor());
            }
        }
        return valorTotal != null ? valorTotal : BigDecimal.ZERO;
    }

    public BigDecimal getAvg(List<TransacaoDTO> transacoes) {
        return getSum(transacoes).divide(BigDecimal.valueOf(transacoes.size()), 2, RoundingMode.HALF_EVEN);
    }
}
