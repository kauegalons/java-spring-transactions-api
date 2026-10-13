package com.kauegalons.transactions.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EstatisticaDTO {

    private final long count;
    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal min;
    private final BigDecimal max;

    public EstatisticaDTO(long count, BigDecimal sum, BigDecimal avg, BigDecimal min, BigDecimal max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }
}
