package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.TransacaoDTO;
import com.kauegalons.transactions.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoServiceTest {
    private TransacaoService transacaoService;
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setUp() {
        transacaoRepository = new TransacaoRepository();
        transacaoService = new TransacaoService(transacaoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoValorForNegativo() {
        TransacaoDTO transacao = new TransacaoDTO(new BigDecimal("-1"), OffsetDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacao));
    }

    @Test
    void deveLancarExcecaoQuandoValorForNulo() {
        TransacaoDTO transacao = new TransacaoDTO(null, OffsetDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacao));
    }

    @Test
    void deveLancarExcecaoQuandoDataHoraForNula() {
        TransacaoDTO transacao = new TransacaoDTO(new BigDecimal("1"), null);

        assertThrows(IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacao));
    }

    @Test
    void deveLancarExcecaoQuandoDataHoraForFuturo() {
        TransacaoDTO transacao = new TransacaoDTO(new BigDecimal("1"), OffsetDateTime.now().plusMinutes(2));

        assertThrows(IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacao));
    }

    @Test
    void deveAceitarQuandoValorForZero() {
        TransacaoDTO transacao = new TransacaoDTO(new BigDecimal("0"), OffsetDateTime.now());

        assertDoesNotThrow(() -> transacaoService.criarTransacao(transacao));
        assertEquals(1, transacaoRepository.getListaTransacoes().size());

    }

    @Test
    void deveAceitarTransacaoValida() {
        TransacaoDTO transacao = new TransacaoDTO(new BigDecimal("10"), OffsetDateTime.now());

        assertDoesNotThrow(() -> transacaoService.criarTransacao(transacao));
        assertEquals(1, transacaoRepository.getListaTransacoes().size());
    }



}