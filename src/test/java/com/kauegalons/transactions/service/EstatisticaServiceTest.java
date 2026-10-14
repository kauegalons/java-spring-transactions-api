package com.kauegalons.transactions.service;

import com.kauegalons.transactions.dto.EstatisticaDTO;
import com.kauegalons.transactions.model.Transacao;
import com.kauegalons.transactions.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EstatisticaServiceTest {
    private EstatisticaService estatisticaService;
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setUp() {
        transacaoRepository = new TransacaoRepository();
        estatisticaService = new EstatisticaService(transacaoRepository);
    }


    @Test
    void deveRetornarZerosQuandoNaoHouverTransacoes() {

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(0, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("0");
        assertThat(resultado.getAvg()).isEqualByComparingTo("0.000");
        assertThat(resultado.getMin()).isEqualByComparingTo("0");
        assertThat(resultado.getMax()).isEqualByComparingTo("0");
    }

    @Test
    void deveCalcularEstatisticasDeUmaTransacao(){
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), OffsetDateTime.now()));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(1, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("10");
        assertThat(resultado.getAvg()).isEqualByComparingTo("10.000");
        assertThat(resultado.getMin()).isEqualByComparingTo("10");
        assertThat(resultado.getMax()).isEqualByComparingTo("10");
    }

    @Test
    void deveCalcularEstatisticasDeVariasTransacoes() {
        OffsetDateTime agora = OffsetDateTime.now();
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("20"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("30"), agora));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(3, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("60");
        assertThat(resultado.getAvg()).isEqualByComparingTo("20.000");
        assertThat(resultado.getMin()).isEqualByComparingTo("10");
        assertThat(resultado.getMax()).isEqualByComparingTo("30");
    }

    @Test
    void deveConsiderarApenasTransacoesDosUltimos60Segundos(){

        OffsetDateTime agora = OffsetDateTime.now();
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), agora.minusSeconds(61)));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("20"), agora.minusSeconds(30)));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("30"), agora));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(2, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("50");
        assertThat(resultado.getAvg()).isEqualByComparingTo("25.000");
        assertThat(resultado.getMin()).isEqualByComparingTo("20");
        assertThat(resultado.getMax()).isEqualByComparingTo("30");
    }

    @Test
    void deveRetornarZerosQuandoTodasTransacoesForemAntigas(){

        OffsetDateTime agora = OffsetDateTime.now();
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), agora.minusSeconds(61)));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("20"), agora.minusSeconds(62)));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("30"), agora.minusSeconds(63)));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(0, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("0");
        assertThat(resultado.getAvg()).isEqualByComparingTo("0.000");
        assertThat(resultado.getMin()).isEqualByComparingTo("0");
        assertThat(resultado.getMax()).isEqualByComparingTo("0");
    }

    @Test
    void deveArredondarMediaComTresCasasDecimais(){

        OffsetDateTime agora = OffsetDateTime.now();
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("10"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("20"), agora));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertThat(resultado.getAvg()).isEqualByComparingTo("13.333");
    }

    @Test
    void deveConsiderarTransacaoComValorZero(){

        OffsetDateTime agora = OffsetDateTime.now();
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("0"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("20"), agora));
        transacaoRepository.salvarTransacao(new Transacao(new BigDecimal("30"), agora));

        EstatisticaDTO resultado = estatisticaService.getEstatisticas();

        assertEquals(3, resultado.getCount());
        assertThat(resultado.getSum()).isEqualByComparingTo("50");
        assertThat(resultado.getAvg()).isEqualByComparingTo("16.667");
        assertThat(resultado.getMin()).isEqualByComparingTo("0");
        assertThat(resultado.getMax()).isEqualByComparingTo("30");
    }

}