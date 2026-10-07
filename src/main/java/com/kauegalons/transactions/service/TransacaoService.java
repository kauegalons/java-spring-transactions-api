package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.TransacaoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class TransacaoService {

    public void validarTransacao(TransacaoDTO transacaoDTO) {

        if (transacaoDTO.getValor() == null) {
            throw new IllegalArgumentException("Valor da transação é obrigatório.");
        }

        if (transacaoDTO.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora da transação são obrigatórias.");
        }

        if (transacaoDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor da transação não pode ser negativo.");
        }

        if (transacaoDTO.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da transação não podem ser futuras.");
        }

    }
}
