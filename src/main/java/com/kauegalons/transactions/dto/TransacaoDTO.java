package com.kauegalons.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
        private BigDecimal valor;
        private OffsetDateTime dataHora;

}
