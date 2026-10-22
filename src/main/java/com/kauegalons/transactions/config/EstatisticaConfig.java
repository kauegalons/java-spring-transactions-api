package com.kauegalons.transactions.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "estatistica")
@Validated
public record EstatisticaConfig(
        @NotNull
        @Positive
        Integer segundos) {


}
