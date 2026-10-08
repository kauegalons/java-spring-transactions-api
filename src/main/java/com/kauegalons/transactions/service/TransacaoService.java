package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.TransacaoDTO;
import com.kauegalons.transactions.model.Transacao;
import com.kauegalons.transactions.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public void criarTransacao(TransacaoDTO transacaoDTO) {
        validarTransacao(transacaoDTO);

        Transacao novaTransacao = new Transacao(transacaoDTO.getValor(), transacaoDTO.getDataHora());
        transacaoRepository.salvarTransacao(novaTransacao);
    }


    private void validarTransacao(TransacaoDTO transacaoDTO) {

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

    public void apagarTodasTransacoes() {
        transacaoRepository.apagarTodasTransacoes();
    }
}
