package com.kauegalons.transactions.dto;

import lombok.Getter;

@Getter
public class EstatisticaDTO {

    private final long count;
    private final double sum;
    private final double avg;
    private final double min;
    private final double max;

    public EstatisticaDTO(long count, double sum, double avg, double min, double max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }
}
