package com.kauegalons.transactions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                .info(new Info()
                        .title("Transactions API")
                        .description("API for managing transactions and statistics")
                        .version("1.0.0"));
    }

}
